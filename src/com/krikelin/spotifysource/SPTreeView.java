/*
 * Copyright (C) 2011 Alexander Forselius
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.krikelin.spotifysource;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class SPTreeView extends JList<Object> {
	public class SPCellRenderer implements ListCellRenderer<Object>
	{

		@Override
		public Component getListCellRendererComponent(JList<?> arg0, Object arg1,
				int arg2, boolean arg3, boolean arg4) {
			// TODO Auto-generated method stub
			JPanel bc = new JPanel();
			SimpleEntry se = ((SimpleEntry)arg1);
			ImageBox icon = new ImageBox(SPTreeView.this.getContext().getSkin().getImageById(DefaultSkin.ICON_START+1), SPTreeView.this.getContext());
			if(se.getIcon() != null){
				icon = new ImageBox(se.getIcon(), SPTreeView.this.getContext());
			}
			
			bc.setLayout(null);
			bc.setPreferredSize(new Dimension(200,16));
			SPLabel c = new SPLabel(SPTreeView.this.mContext,"s");
			c.setText(((SimpleEntry)arg1).getUri().getTitle());
			Color bgColor =  arg3 ?   SPTreeView.this.getContext().getSkin().getSelectionBg(): SPTreeView.this.getContext().getSkin().getBackgroundColor();
			bc.setBackground(bgColor);
			c.setBackground(bgColor);
			c.setColor( arg3 ?SPTreeView.this.getContext().getSkin().getSelectionFg()  :   SPTreeView.this.getContext().getSkin().getForeColor() );
			c.setBounds(new Rectangle(16,0,100,32));
			c.setShadow(!arg3);
			bc.add(c);
			add(icon);
			return bc;
		}
		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -3552362935105235025L;
	@SuppressWarnings("unchecked")
	public SPTreeView(SpotifyWindow mContext, ArrayList<ISPEntry> mListModel)
	{
		super(new SPListModel(mListModel));
		this.mContext=mContext; 
		setCellRenderer(new SPCellRenderer());
		setDragEnabled(true);
		
		// Set skin
		setBackground(getContext().getSkin().getBackgroundColor());
		setForeground(getContext().getSkin().getForeColor());
		setSelectionBackground(getContext().getSkin().getSelectionBg());
		setSelectionForeground(getContext().getSkin().getSelectionFg());
	}
	public SpotifyWindow getContext()
	{
		return mContext;
	}
	private SpotifyWindow mContext;
}
