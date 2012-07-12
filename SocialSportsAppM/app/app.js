//Ext.regApplication({
//SocialSports = new Ext.regApplication({
SocialSports = new Ext.regApplication({
	name: 'SocialSports', 
	defaultUrl: 'Home/timeline',
	launch: function()
	{
		this.views.viewport = new this.views.Viewport();
		
		this.views.teamsList = this.views.viewport.down('#teamsList');
		this.views.teamsForm = this.views.viewport.down('#teamsForm');
		this.views.playersList = this.views.viewport.down('#playersList');
		this.views.playersForm = this.views.viewport.down('#playersForm');
		this.views.eventsList = this.views.viewport.down('#eventsList');
		this.views.eventsForm = this.views.viewport.down('#eventsForm');
		
		this.views.hashTeamsList = this.views.viewport.down('#hashTeamsList');
		this.views.hashTeamsForm = this.views.viewport.down('#hashTeamsForm');
		this.views.hashPlayersList = this.views.viewport.down('#hashPlayersList');
		this.views.hashPlayersForm = this.views.viewport.down('#hashPlayersForm');
		this.views.hashEventsList = this.views.viewport.down('#hashEventsList');
		this.views.hashEventsForm = this.views.viewport.down('#hashEventsForm');
				
	},
});