package scripts.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSTile;

public class AreaBounds {
	/** List of walkable tiles */
	private RSTile[] walkableTiles;
	
	/** List of nonwalkable tiles */
	private RSTile[] nonWalkableTiles;
	
	/** Root tile used to create AreaBounds */
	private RSTile root;
	
	/** Internal tile lookup map */
	private Map<RSTile, Boolean> tileLookup;
	
	/** Max distance area will scan for tiles from root */
	private final static int MAX_DISTANCE = 24;
	
	public AreaBounds(Positionable position) {
		RSTile start = position.getPosition();

		root = start;
		tileLookup = new HashMap<>();
		List<RSTile> walkable = new ArrayList<>();
		List<RSTile> nonWalkable = new ArrayList<>();
		fillTiles(start, walkable, nonWalkable);

		this.walkableTiles = walkable.toArray(new RSTile[walkable.size()]);
		this.nonWalkableTiles = nonWalkable.toArray(new RSTile[nonWalkable.size()]);
	}
	
	private void fillTiles(RSTile tile, List<RSTile> walkable, List<RSTile> nonWalkable) {
		if ( tile == null )
			return;
		
		if ( tile.distanceTo(root) > MAX_DISTANCE )
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
		RSTile nonwalk = chestAdjacentNonWalkableTile(positionable.getPosition());
		if ( nonwalk == null )
			nonwalk = checkAdjacentWalkableTile(positionable.getPosition());
			
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

	/**
	 * Returns the tile that was used to build this AreaBounds originally.
	 * @return
	 */
	public RSTile getRoot() {
		return this.root;
	}

	/**
	 * Return the nearest walkable tile to a given RSTile within the area.
	 * @param position
	 * @return
	 */
	public RSTile checkAdjacentWalkableTile(RSTile position) {
		if ( position == null )
			return null;
			
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

	/**
	 * Returns the nearest nonwalkable RSTile within the area.
	 * @param position
	 * @return
	 */
	public RSTile chestAdjacentNonWalkableTile(RSTile position) {
		if ( position == null )
			return null;
			
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
	
	public boolean equals(AreaBounds bounds) {
		if ( bounds == null )
			return false;
		
		if (!(bounds instanceof AreaBounds))
			return false;
		
		return bounds.tileLookup.equals(this.tileLookup);
	}
}
