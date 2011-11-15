package com.krikelin.spotifysource;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class SPTreeView extends JList {
	public class SPCellRenderer implements ListCellRenderer
	{

		@Override
		public Component getListCellRendererComponent(JList arg0, Object arg1,
				int arg2, boolean arg3, boolean arg4) {
			// TODO Auto-generated method stub
			JPanel bc = new JPanel();
			ImageBox icon = new ImageBox(getContext().getSkin().getImageById(DefaultSkin.ICON_START+1),SPTreeView.this.getContext());
			icon.setBounds(new Rectangle(0,0,16,16));
			
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
