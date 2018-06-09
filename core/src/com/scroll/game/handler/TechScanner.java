package com.scroll.game.handler;

import com.scroll.game.tech.Tech;

public class TechScanner {

	public static Tech scanTech(Tech[][] techTree, Tech currentTech, String name) {
		for(int y = 0; y < techTree.length; y++) {
			for(int x = 0; x < techTree[y].length; x++) {
				if(techTree[y][x].getName().equals(name)) {
					System.out.println(currentTech.getName() + " requires " + techTree[y][x].getName());
					return techTree[y][x];
				}
			}
		}
		return null;
	}
}
