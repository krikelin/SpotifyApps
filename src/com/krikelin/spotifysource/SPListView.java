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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
public class SPListView extends JTable implements SPWidget, SPPart {
	private int id;
	public class SPColumnRenderer extends JLabel implements TableCellRenderer
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = -3355510620608500742L;

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// TODO Auto-generated method stub
			setText(getModel().getColumnName(column));
			return this;
		}
		@Override
		public void paint(Graphics g)
		{
			try{
				g.drawImage(SPListView.this.getContext().getSkin().getImageById(DefaultSkin.IMG_CHEADER),0,0,getWidth(),23,null);
				SPListView.this.getContext().getSkin().drawText(getText(),Color.black,g,getWidth()/2 - g.getFontMetrics().stringWidth(getText())/2,10,Color.white);
			}
			catch(Exception e){
				
			}
		}		
	}
	public class SPCellRenderer extends JLabel implements TableCellRenderer
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = -5199400905262270109L;

		@Override
		public Component getTableCellRendererComponent(JTable arg0,
				Object arg1, boolean isSelected, boolean hasFocus,final int row,final int column) {
			// TODO Auto-generated method stub
			SPLabel lbl = new SPLabel(SPListView.this.getContext(),"");
			
			Object resource = SPListView.this.getModel().getValueAt(row, column); // the resource to deal with
			if(resource == null)
				return this;
			lbl.setText(resource.toString());
			Color bgColor = isSelected ? SPListView.this.getContext().getSkin().getSelectionBg() : (((row % 2) == 0)  ? SPListView.this.getContext().getSkin().getBackgroundColor() : SPListView.this.getContext().getSkin().getAlternateBgColor());
			Color fgColor =isSelected ?   SPListView.this.getContext().getSkin().getSelectionFg() :  SPListView.this.getContext().getSkin().getForeColor();
			
			/** 
			 * SSet the colors of the entry 
			 */
			if(getModel() instanceof SPTableModel){
				ISPEntry sourceEntry = (ISPEntry)((SPTableModel)getModel()).getData(row);
				boolean isPlaying = (SPListView.this.getContext().getCurrentPlayingEntry() == (sourceEntry));
				
				bgColor =  isSelected ?  SPListView.this.getContext().getSkin().getSelectionBg() : isPlaying ? SPListView.this.getContext().getSkin().getPlayingBg() : (((row % 2) == 0)  ? SPListView.this.getContext().getSkin().getBackgroundColor() : SPListView.this.getContext().getSkin().getAlternateBgColor() );
				fgColor = isSelected ? SPListView.this.getContext().getSkin().getSelectionFg() :  isPlaying ? SPListView.this.getContext().getSkin().getPlayingFg() : SPListView.this.getContext().getSkin().getForeColor();
				try{
					// if is playing
					if(column > 1 && sourceEntry.getAuthorUri().toLinkString() == "spotify:underfined")
					{
						fgColor = fgColor.darker();
					}
				}catch(Exception e){
					
				}
				/*
				 * If the entry is popularity, draw popularity
				 * if the entry is length draw length
				 * 	
				 */
				if(resource!=null)
					if(resource instanceof URI)
					{
						if(((URI)resource).getTitle()!= null)
							if(!((URI)resource).getTitle().isEmpty() && column > 0)
							{
								lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
							}
						if(((URI)resource).getTitle()!=null)
							lbl.setText(((URI)resource).getTitle());
					}
					if(resource instanceof Duration)
					{
						setText(((Duration)resource).toString());
					}
					if(resource instanceof Float)
					{					
						PopularityMeter pe = new PopularityMeter(SPListView.this.getContext());
						pe.setPopularity((Float)resource);
						return pe;
					}
				
					
					
					
			}
			lbl.setFont(SPListView.this.getContext().getSkin().getFont());
			lbl.setBackground(bgColor);
			lbl.setForeground(fgColor);
			lbl.setBounds(getX()-1,getY()-1,getWidth()+2,getHeight()+2);
			return lbl;
		}
		
	}
	
	
	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
		// TODO Auto-generated method stub

		{
			return new SPCellRenderer();
		}
		
	}
	private SpotifyWindow mContext;
	public SPListView(TableModel tableModel,SpotifyWindow mContext)
	{
		super(tableModel);
		
		
		setDragEnabled(true);
		setRowMargin(0);
		 
		
		this.mContext=mContext;
		//setDefaultRenderer(SPCellRenderer.class,new SPCellRenderer());
		setBackground(mContext.getSkin().getBackgroundColor());
		setForeground(mContext.getSkin().getForeColor());
		setSelectionBackground(mContext.getSkin().getSelectionBg());
		updateUI();
		repaint();
		for(int i=0; i < getColumnModel().getColumnCount(); i++)
		{
			getColumnModel().getColumn(i).setHeaderRenderer(new SPColumnRenderer());
		}
	
		setSelectionForeground(mContext.getSkin().getSelectionFg());
		setBorder(BorderFactory.createEmptyBorder());
		
	
		addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				int row = rowAtPoint(new Point(arg0.getX(),arg0.getY()));
				int cell = columnAtPoint(new Point(arg0.getX(),arg0.getY()));
				Object href = getModel().getValueAt(row,cell);
				if(href instanceof URI)
				{
					if(((URI)href).getTitle() != null && ((URI)href).getTitle() != "" &&  cell > 0)
					{
						setCursor(new Cursor(Cursor.HAND_CURSOR));
					}
					else
					{
						setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}
				}
			}
			
		});
		addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// get f
				int row = rowAtPoint(new Point(arg0.getX(),arg0.getY()));
				int cell = columnAtPoint(new Point(arg0.getX(),arg0.getY()));
					// TODO Auto-generated method stub
				Object href = getModel().getValueAt(row,cell);
				if(getModel() instanceof SPTableModel){
					Object data = ((SPTableModel)getModel()).getData(row);
					if(arg0.getClickCount() == 2)
					{
						// If the row type is an song, play it
						if( data instanceof ISPEntry)
						{
							getContext().playSong((ISPEntry)data);
							return;
						}
					}
					if(href instanceof URI)
					{
						URI c = (URI)href;
						SPListView.this.getContext().navigate(c);	
					}
				}
					
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		setIntercellSpacing(new Dimension(0,0));
		setShowGrid(false);
	
		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 7253805377086218139L;

	@Override
	public SpotifyWindow getContext() {
		// TODO Auto-generated method stub
		return mContext;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SPOnClickListener getOnClickListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLabel(String label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOnClickListener(SPOnClickListener listener) {
		// TODO Auto-generated method stub
		
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	@Override
	public JComponent findViewById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
