package com.anrisoftware.globalpom.format.degree;

/**
 * Directions from the equator/prime meridian.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
enum Direction {

	N(1.0), S(-1.0), E(1.0), W(-1.0);

	private double direction;

	private Direction(double direction) {
		this.direction = direction;
	}

	public double getDirection() {
		return direction;
	}
}
