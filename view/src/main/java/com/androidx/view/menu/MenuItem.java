package com.androidx.view.menu;

import android.view.View;

/**
 * Created by xiaoqi on 2017/12/19.
 */

public class MenuItem {

	private String item;
	private int iteresId = View.NO_ID;


	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getIteresId() {
		return iteresId;
	}

	public void setIteresId(int iteresId) {
		this.iteresId = iteresId;
	}
}
