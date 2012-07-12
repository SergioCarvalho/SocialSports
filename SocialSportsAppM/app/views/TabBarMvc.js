/**
* Script name:	TabBarMvc
* 
* Description:	Custom Sencha Touch TabBar that works with a MVC structured application;
* 				Full description can be found here:
* 				http://www.onlinesolutionsdevelopment.com/blog/mobile-development/javascript/sencha-touch-tabbar-in-a-mvc-structured-application
* 
* Author:		CAM
* Author URL:	http://www.onlinesolutionsdevelopment.com/blog
*/

TabBarMvc = Ext.extend(Ext.TabBar, {
	dock: 'bottom',
	ui: 'dark',
	layout: {
		pack: 'center'
	},
	
	initComponent: function()
	{
		var thisComponent = this;
		
		// iterate through all the items
		Ext.each(this.items, function(item)
		{
			// add a handler function for the tab button
			item.handler = function(){
				thisComponent.tabButtonHandler(this);
			};
			
		}, this);
		
		// detect when the route changed
		Ext.Dispatcher.on('dispatch', function(interaction)
		{
			var tabs = this.query('.tab');
			
			Ext.each(tabs, function(item)
			{
				if (interaction.historyUrl == item.route)
				{
					this.setActiveTab(item);
					return false;
				}
			}, this);
			
		}, this);
		
		TabBarMvc.superclass.initComponent.apply(this, arguments);
	},
	
	// function called on tab button tap
	tabButtonHandler: function(tab)
	{
		this.setActiveTab(tab);
		
		if ( ! Ext.isEmpty(tab.route))
		{
			Ext.redirect(tab.route);
		}
	},
	
	setActiveTab: function(tab)
	{ 
		var tabs = this.query('.tab');
	   
		Ext.each(tabs, function(item){
			item.removeCls('x-tab-active');
		}, this);
	 
		tab.addCls('x-tab-active');
	}
});

Ext.reg('TabBarMvc', TabBarMvc);