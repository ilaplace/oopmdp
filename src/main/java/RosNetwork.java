import burlap.behavior.functionapproximation.DifferentiableStateActionValue;
import burlap.behavior.functionapproximation.dense.ConcatenatedObjectFeatures;
import burlap.behavior.functionapproximation.dense.NumericVariableFeatures;
import burlap.behavior.functionapproximation.sparse.tilecoding.TileCodingFeatures;
import burlap.behavior.functionapproximation.sparse.tilecoding.TilingArrangement;
import burlap.behavior.policy.Policy;
import burlap.behavior.policy.PolicyUtils;
import burlap.behavior.policy.RandomPolicy;
import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.learning.tdmethods.vfa.GradientDescentSarsaLam;
import burlap.mdp.core.state.*;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.ros.RosEnvironment;
import burlap.ros.actionpub.ActionStringPublisher;
import burlap.shell.EnvironmentShell;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.java.browser.plugin2.DOM;
import ros.RosBridge;
import ros.RosListenDelegate;
import ros.msgs.std_msgs.PrimitiveMsg;
import ros.tools.MessageUnpacker;
/**
 * Created by semih on 21.04.2017.
 */
public class RosNetwork {

    public RosNetwork() {

        final Domain domain_ = new Domain();
        OOSADomain domain =  domain_.generateDomain();

        String uri = "ws://localhost:9090";
        String stateTopic = "/burlap_state";
        String actionTopic = "/burlap_action";
        String state = "tut/dof";

        RosEnvironment env = new RosEnvironment(domain,uri, stateTopic, state) {
            @Override
            public State unpackStateFromMsg(JsonNode jsonNode, String s){
                    MessageUnpacker<Agent> unpacker = new MessageUnpacker<Agent>(Agent.class);
                    Agent data = unpacker.unpackRosMessage(jsonNode);
                    if (data.p_values.length != 0)
                        System.out.print("DOF values: " + Arrays.toString(data.p_values) +"\n"
                            + "Quality: " + Arrays.toString(data.quality) + "\n\n");
                    Domain.quality = data.quality;
                float[] changeInDof = {0.0f,0.0f,0.0f,0.0f};

                for (int i = 0; i < Domain.p_values.length; i++) {
                    changeInDof[i] = data.p_values[i] - Domain.p_values[i];
                }
                System.out.println(Arrays.toString(changeInDof));
                    Domain.p_values = data.p_values;
                    return unpacker.unpackRosMessage(jsonNode);}};

        env.setActionPublisherForMultipleActions(domain.getActionTypes(),
                new ActionStringPublisher(actionTopic,env.getRosBridge(),5));

        ConcatenatedObjectFeatures inputFeatures = new ConcatenatedObjectFeatures()
                .addObjectVectorizion(Domain.CLASS_AGENT, new NumericVariableFeatures());

        int nTiling = Domain.DOF;
        double resolution = 1;

        Domain.RobotParams prm = new Domain.RobotParams();
        double[] widths =  new double[Domain.DOF];
        for (int i = 0; i < Domain.DOF; i++) {
            widths[i] = (prm.getP_max() - prm.getP_minim())/resolution;
        }

        TileCodingFeatures tileCodingFeatures = new TileCodingFeatures(inputFeatures);
        tileCodingFeatures.addTilingsForAllDimensionsWithWidths(widths, nTiling,
                TilingArrangement.RANDOM_JITTER);

        double defaultQ = 0.5;
        DifferentiableStateActionValue vfa =
                tileCodingFeatures.generateVFA(defaultQ/nTiling);

        GradientDescentSarsaLam agent =
                new GradientDescentSarsaLam(domain,0.99, vfa,
                        0.01,0.5);

        env.setRewardFunction(new GraspRF(domain));
        env.setTerminalFunction(new GraspTF(domain));

        List episodes = new ArrayList();

        for (int i = 0; i < 100; i++) {
            try {
                Episode ea = agent.runLearningEpisode(env);
                episodes.add(ea);
                System.out.println(i + ea.maxTimeStep());
                env.resetEnvironment();
            }
            catch (NullPointerException e ) {
               // e.printStackTrace();
                System.out.println("Did you quit?");
            }
        }
    }
    public static void main(String[] args) {
        RosNetwork ros = new RosNetwork();

    }

}
