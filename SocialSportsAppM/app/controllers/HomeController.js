Ext.regController('Home', {

	//timeline action
	timeline: function(options)
	{
		//Ext.Msg.alert('Test', "Home's index action was called");
		if( ! this.timelineView)
		{
			this.timelineView = this.render({
				xtype:'HomeTimeline',
			});
		}
		
		this.application.views.viewport.setActiveItem(this.timelineView, options.animation);
		
		var backButton = this.application.views.viewport.query('#backBtn')[0];
		backButton.hide();
	},
	
	//new message action
	newmessage: function()
	{
		//Ext.Msg.alert('Test', "Home's index action was called");
		if( ! this.newMessageView)
		{
			this.newMessageView = this.render({
				xtype:'HomeNewMessage',
			});
		}
		
		this.application.views.viewport.setActiveItem(this.newMessageView);
		
		var backButton = this.application.views.viewport.query('#backBtn')[0];
		backButton.hide();
	},
	
	//favourites action
	favourites : function()
	{
		if( ! this.homeFavouritesView)
		{
			this.homeFavouritesView = this.render({
				xtype:'HomeFavourites',
			});
		}
		
		this.application.views.viewport.setActiveItem(this.homeFavouritesView);
		
		var backButton = this.application.views.viewport.query('#backBtn')[0];
		backButton.hide();
	},
	
	//preferences action
	preferences : function()
	{
		if( ! this.homePreferencesView)
		{
			this.homePreferencesView = this.render({
				xtype:'HomePreferences',
			});
		}
		
		this.application.views.viewport.setActiveItem(this.homePreferencesView);
		
		var backButton = this.application.views.viewport.query('#backBtn')[0];
		
		backButton.hide();
	},
	//instructions action
	instructions : function()
	{
		if( ! this.homeInstructionsView)
		{
			this.homeInstructionsView = this.render({
				xtype:'HomeInstructions',
			});
		}
		
		this.application.views.viewport.setActiveItem(this.homeInstructionsView);
		
		var backButton = this.application.views.viewport.query('#backBtn')[0];
		backButton.hide();
	},
/*	about: function()
	{
		if( ! this.aboutView)
		{
			this.aboutView = this.render({
				xtype: 'HomeAbout',
			});	
		}
		this.application.viewport.setActiveItem(this.aboutView);
		
		var backButton = this.application.viewport.query('#backBtn')[0];
		
		backButton.show();
		
		backButton.setHandler(function()
		{
				Ext.dispatch({
					controller: 'Home',
					action: 'timeline',
					historyUrl: 'Home/timeline',
					animation:{
						type:'slide',
						reverse: true,
					},
				});
		});
	},*/
});