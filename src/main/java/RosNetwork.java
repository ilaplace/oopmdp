import burlap.behavior.policy.Policy;
import burlap.behavior.policy.PolicyUtils;
import burlap.behavior.policy.RandomPolicy;
import burlap.mdp.core.state.*;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.ros.RosEnvironment;
import burlap.ros.actionpub.ActionStringPublisher;
import com.fasterxml.jackson.databind.JsonNode;
//import org.msgpack.core.MessagePacker;
//import org.msgpack.core.MessageUnpacker;
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

//        OOSADomain domain = new OOSADomain();
        Domain domain_ = new Domain();
        OOSADomain domain =  domain_.generateDomain();
        String uri = "ws://localhost:9090";
        String stateTopic = "/burlap_state";
        String stateMessage = "std_msgs/String";
        String actionTopic = "/burlap_action";
        String state = "tut/dof";

        RosEnvironment env = new RosEnvironment(domain,uri, stateTopic, state) {


            @Override
            public State unpackStateFromMsg(JsonNode jsonNode, String s){
                    MessageUnpacker<Agent> unpacker = new MessageUnpacker<Agent>(Agent.class);
                    Agent data = unpacker.unpackRosMessage(jsonNode);
                    if (data.p_values.length != 0)
                        System.out.print(data.p_values[1]+"\n\n");

                    return unpacker.unpackRosMessage(jsonNode);
            }
        };
        env.setActionPublisherForMultipleActions(domain.getActionTypes(),
                new ActionStringPublisher(actionTopic,env.getRosBridge(),500));
        Policy randPolicy = new RandomPolicy(domain);
        PolicyUtils.rollout(randPolicy, env, 100);

    }


    public static void main(String[] args) {
        RosNetwork ros = new RosNetwork();

    }

}
