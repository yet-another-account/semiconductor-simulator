package game.circuitsimulator.design;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestLayer {
	@Test
	public void testConnectSilicon() {
		Layer l = new Layer(10, 10);
		l.setNSilicon(0, 0);
		l.setNSilicon(0, 1);
		
		Silicon bottom = l.getSiliconAt(0, 0);
		Silicon top = l.getSiliconAt(0, 1);
		
		l.connectSilicon(0, 0, Direction.NORTH);

		assertThat(top.isConnected(Direction.SOUTH), is(true));
		assertThat(top.isConnected(Direction.NORTH), is(not(true)));
		assertThat(bottom.isConnected(Direction.NORTH), is(true));
		assertThat(bottom.isConnected(Direction.SOUTH), is(not(true)));
		
		// Test junctions
		
		l.setPSilicon(1, 1);
		
		l.connectSilicon(0, 1, Direction.EAST);
		
		assertThat(l.getSiliconAt(1, 1).getType(), is(equalTo(SiliconType.JUNC_PNP)));
		
		l.connectSilicon(1, 1, Direction.WEST);
		
		assertThat(l.getSiliconAt(1, 1).getType(), is(equalTo(SiliconType.JUNC_PNP)));
		assertThat(l.getSiliconAt(1, 1).isConnected(Direction.WEST), is(true));
		
		l.setPSilicon(1, 0);
		
		l.connectSilicon(1, 0, Direction.WEST);

		assertThat(l.getSiliconAt(0, 0).getType(), is(equalTo(SiliconType.JUNC_NPN)));
		assertThat(l.getSiliconAt(0, 0).isConnected(Direction.EAST), is(true));
		
	}
	
	@Test
	public void testRemoveSilicon() {
		Layer l = new Layer(10, 10);
		l.setNSilicon(1, 1);
		l.setNSilicon(0, 1);
		l.setNSilicon(1, 0);
		
		Silicon middle = l.getSiliconAt(1, 1);
		Silicon left = l.getSiliconAt(0, 1);
		Silicon bottom = l.getSiliconAt(1, 0);

		// now all are connected
		l.connectSilicon(1, 1, Direction.SOUTH);
		l.connectSilicon(1, 1, Direction.WEST);

		assertThat(middle.isConnected(Direction.WEST), is(true));
		assertThat(left.isConnected(Direction.EAST), is(true));
		assertThat(middle.isConnected(Direction.SOUTH), is(true));
		assertThat(bottom.isConnected(Direction.NORTH), is(true));
		
		l.removeSilicon(1, 1);
		
		middle = l.getSiliconAt(1, 1);
		left = l.getSiliconAt(0, 1);
		bottom = l.getSiliconAt(1, 0);
		
		assertThat(left.isConnected(Direction.EAST), is(not(true)));
		assertThat(bottom.isConnected(Direction.NORTH), is(not(true)));
		assertThat(middle, is(equalTo(null)));
		
	}
	
	@Test
	public void testConnectMetal() {
		Layer l = new Layer(10, 10);
		l.setMetal(0, 0);
		l.setMetal(0, 1);
		
		Metal bottom = l.getMetalAt(0, 0);
		Metal top = l.getMetalAt(0, 1);
		
		l.connectMetal(0, 0, Direction.NORTH);

		assertThat(top.isConnected(Direction.SOUTH), is(true));
		assertThat(top.isConnected(Direction.NORTH), is(not(true)));
		assertThat(bottom.isConnected(Direction.NORTH), is(true));
		assertThat(bottom.isConnected(Direction.SOUTH), is(not(true)));
		
	}
	
	@Test
	public void testRemoveMetal() {
		Layer l = new Layer(10, 10);
		l.setMetal(1, 1);
		l.setMetal(0, 1);
		l.setMetal(1, 0);
		
		Metal middle = l.getMetalAt(1, 1);
		Metal left = l.getMetalAt(0, 1);
		Metal bottom = l.getMetalAt(1, 0);

		// now all are connected
		l.connectMetal(1, 1, Direction.SOUTH);
		l.connectMetal(1, 1, Direction.WEST);

		assertThat(middle.isConnected(Direction.WEST), is(true));
		assertThat(left.isConnected(Direction.EAST), is(true));
		assertThat(middle.isConnected(Direction.SOUTH), is(true));
		assertThat(bottom.isConnected(Direction.NORTH), is(true));
		
		l.removeMetal(1, 1);
		
		middle = l.getMetalAt(1, 1);
		left = l.getMetalAt(0, 1);
		bottom = l.getMetalAt(1, 0);
		
		assertThat(left.isConnected(Direction.EAST), is(not(true)));
		assertThat(bottom.isConnected(Direction.NORTH), is(not(true)));
		assertThat(middle, is(equalTo(null)));
		
	}
}
