package scripts.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tribot.api.General;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSTile;

public class AreaBounds {
	private RSTile[] walkableTiles;
	private RSTile[] nonWalkableTiles;
	private RSTile root;
	
	private Map<RSTile, Boolean> tileLookup;
	
	public AreaBounds(Positionable position) {
		RSTile start = position.getPosition();

		root = start;
		tileLookup = new HashMap<>();
		List<RSTile> walkable = new ArrayList<>();
		List<RSTile> nonWalkable = new ArrayList<>();
		fillTiles(start, walkable, nonWalkable);

		this.walkableTiles = walkable.toArray(new RSTile[walkable.size()]);
		this.nonWalkableTiles = nonWalkable.toArray(new RSTile[nonWalkable.size()]);
		
		General.println("Generated AreaBounds: " + getWalkableTiles().length + " / " + getNonWalkableTiles().length);
	}
	
	private void fillTiles(RSTile tile, List<RSTile> walkable, List<RSTile> nonWalkable) {
		if ( tile == null )
			return;
		
		if ( tile.distanceTo(root) > 24 )
			return;
		
		Boolean isWalkable = tileLookup.get(tile);
		if ( isWalkable == null )
			isWalkable = PathFinding.isTileWalkable(tile);
		
		if ( !tileLookup.containsKey(tile) )
			if ( isWalkable )
				walkable.add(tile);
			else
				nonWalkable.add(tile);
		else
			return;
		
		tileLookup.put(tile, isWalkable);
		
		if ( isWalkable ) {
			fillTiles(offset(tile, -1, 0), walkable, nonWalkable);
			fillTiles(offset(tile, 1, 0), walkable, nonWalkable);
			fillTiles(offset(tile, 0, 1), walkable, nonWalkable);
			fillTiles(offset(tile, 0, -1), walkable, nonWalkable);
		}
	}
	
	private RSTile offset(RSTile tile, int x, int y) {
		return new RSTile(tile.getX() + x, tile.getY() + y, tile.getPlane(), tile.getType());
	}

	/**
	 * Returns a list of walkable tiles in this area.
	 * @return
	 */
	public RSTile[] getWalkableTiles() {
		return walkableTiles;
	}
	
	/**
	 * Returns a list of non-walkable tiles in this area.
	 * @return
	 */
	public RSTile[] getNonWalkableTiles() {
		return nonWalkableTiles;
	}
	
	/**
	 * Returns whether a tile is inside this area bounds.
	 * @param positionable
	 * @return
	 */
	public boolean contains(Positionable positionable) {
		RSTile position = positionable.getPosition();
		return tileLookup.containsKey(position);
	}
	
	/**
	 * Returns whether a tile is touching the perimeter of the bounds.
	 * @param positionable
	 * @return
	 */
	public boolean isTouching(Positionable positionable) {
		RSTile nonwalk = getNearestNonWalkableTile(positionable.getPosition());
		if ( nonwalk == null )
			nonwalk = getNearestWalkableTile(positionable.getPosition());
			
		return nonwalk != null;
	}
	
	/**
	 * Returns the overall amount of tiles within this area.
	 * @return
	 */
	public int size() {
		return tileLookup.size();
	}
	
	public void debugPaint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		for (RSTile tile : getWalkableTiles()) {
			if (tile.isOnScreen()) {
				g2d.setColor(new Color(0.1f, 0.8f, 0.1f, 0.2f));
				g2d.fill(Projection.getTileBoundsPoly(tile.getPosition(), 0));
				g2d.setColor(new Color(0.1f, 0.8f, 0.1f, 0.66f));
				g2d.draw(Projection.getTileBoundsPoly(tile.getPosition(), 0));
			}
		}
		
		for (RSTile tile : getNonWalkableTiles()) {
			if (tile.isOnScreen()) {
				g2d.setColor(new Color(0.8f, 0.1f, 0.1f, 0.2f));
				g2d.fill(Projection.getTileBoundsPoly(tile.getPosition(), 0));
				g2d.setColor(new Color(0.8f, 0.1f, 0.1f, 0.66f));
				g2d.draw(Projection.getTileBoundsPoly(tile.getPosition(), 0));
			}
		}
	}

	public RSTile getRoot() {
		return this.root;
	}

	public RSTile getNearestWalkableTile(RSTile position) {
		if ( tileLookup.get(position) != null && tileLookup.get(position).booleanValue() )
			return position;
		
		RSTile t1 = offset(position, -1, 0);
		if ( tileLookup.get(t1) != null && tileLookup.get(t1).booleanValue() )
			return t1;
		
		RSTile t2 = offset(position, 1, 0);
		if ( tileLookup.get(t2) != null && tileLookup.get(t2).booleanValue() )
			return t2;
		
		RSTile t3 = offset(position, 0, -1);
		if ( tileLookup.get(t3) != null && tileLookup.get(t3).booleanValue() )
			return t3;
		
		RSTile t4 = offset(position, 0, 1);
		if ( tileLookup.get(t4) != null && tileLookup.get(t4).booleanValue() )
			return t4;
		
		return null;
	}

	public RSTile getNearestNonWalkableTile(RSTile position) {
		if ( tileLookup.get(position) != null && !tileLookup.get(position).booleanValue() )
			return position;
		
		RSTile t1 = offset(position, -1, 0);
		if ( tileLookup.get(t1) != null && !tileLookup.get(t1).booleanValue() )
			return t1;
		
		RSTile t2 = offset(position, 1, 0);
		if ( tileLookup.get(t2) != null && !tileLookup.get(t2).booleanValue() )
			return t2;
		
		RSTile t3 = offset(position, 0, -1);
		if ( tileLookup.get(t3) != null && !tileLookup.get(t3).booleanValue() )
			return t3;
		
		RSTile t4 = offset(position, 0, 1);
		if ( tileLookup.get(t4) != null && !tileLookup.get(t4).booleanValue() )
			return t4;
		
		return null;
	}
}
