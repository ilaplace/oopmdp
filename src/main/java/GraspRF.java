import burlap.mdp.core.action.Action;
import burlap.mdp.core.oo.propositional.PropositionalFunction;
import burlap.mdp.core.oo.state.OOState;
import burlap.mdp.core.state.*;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.mdp.singleagent.oo.OOSADomain;
import com.sun.java.browser.plugin2.DOM;

/**
 * Created by ilaplace on 4/23/17.
 */
public class GraspRF implements RewardFunction {


    private double goalReward = 1000.0;

    private double defaultReward = 0.0;
    private final double closeHandReward = 0.1;

    protected PropositionalFunction graspSucces;

    public GraspRF(OOSADomain domain) {

        this.graspSucces = domain.propFunction(Domain.PF_SUCCES);

    }
    @Override
    public double reward(State s, Action a, State sprime) {

        String action = a.actionName();
//        System.out.println(action);
        String [] actionArray = action.split("_");
        if(Domain.quality[1] !=0){
            System.out.println("GOAL ACHIEVED");
            return goalReward;}
        else if(actionArray[2].equals("PLUS") & !actionArray[1].equals("P0")){
                return closeHandReward;
            }
            return defaultReward;

        }
    public double getGoalReward() {
        return goalReward;
    }
    public double getDefaultReward() {
        return defaultReward;
    }
}
