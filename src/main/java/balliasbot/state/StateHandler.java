package balliasbot.state;

import balliasbot.BalliasBot;
import balliasbot.controls.ControlsOutput;
import balliasbot.data.DataPacket;

public class StateHandler {
	
	public State currentState;
	
	private BalliasBot bot;
	
	public StateHandler(BalliasBot bot) {
		this.bot = bot;
	}
	
	private State selectState(DataPacket data) {
		State states[] = {
				new ChaseBall(data)
		};
		
		for(State state : states) {
			if(state.isViable(data)) {
				return state;
			}
		}
		
		return new ChaseBall(data);
	}
	
	public ControlsOutput execState(DataPacket data) {		
		if(currentState == null) {
			currentState = selectState(data);	
		}

		renderCurrentState();
		
		ControlsOutput stateOutput = currentState.exec(data);
		
		if(stateOutput != null) {
			return stateOutput;
		}
		
		currentState = null;
		
		return execState(data);
	}
	
	private void renderCurrentState() {
		bot.renderer.drawStateString(currentState.getClass().getSimpleName());
	}
	
}
