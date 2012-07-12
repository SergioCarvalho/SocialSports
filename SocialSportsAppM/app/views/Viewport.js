SocialSports.views.Viewport = Ext.extend(Ext.Panel, {
	fullscreen: true,
	layout: 'card',
	cardSwitchAnimation: 'slide',
	
	initComponent: function() {
        Ext.apply(this, {
            items: [
				{ xtype: 'SocialSports.views.TeamsList', id: 'teamsList' },
				{ xtype: 'SocialSports.views.TeamsForm', id: 'teamsForm' },
				{ xtype: 'SocialSports.views.PlayersList', id: 'playersList' },
				{ xtype: 'SocialSports.views.PlayersForm', id: 'playersForm' },
				{ xtype: 'SocialSports.views.EventsList', id: 'eventsList' },
				{ xtype: 'SocialSports.views.EventsForm', id: 'eventsForm' },
				{ xtype: 'SocialSports.views.HashTeamsList', id: 'hashTeamsList' },
				{ xtype: 'SocialSports.views.HashTeamsForm', id: 'hashTeamsForm' },
				{ xtype: 'SocialSports.views.HashPlayersList', id: 'hashPlayersList' },
				{ xtype: 'SocialSports.views.HashPlayersForm', id: 'hashPlayersForm' },
				{ xtype: 'SocialSports.views.HashEventsList', id: 'hashEventsList' },
				{ xtype: 'SocialSports.views.HashEventsForm', id: 'hashEventsForm' },
				
				//{ xtype: 'HomeNewMessage', id: 'homeNewMessage' },
            ]
        });
		
        SocialSports.views.Viewport.superclass.initComponent.apply(this, arguments);
	},
		
		reveal: function(target) {
        this.setActiveItem(
            SocialSports.views[target]
        );
		},
		
		dockedItems: [
		{
			xtype: 'toolbar',
			title: 'Social Sports',
			items: [
				{
					text:'Back',
					itemId:'backBtn',
					ui:'back',
				}
			],
		},
		{
			xtype: 'TabBarMvc',
			items: [
				{
					text:'Timeline Mensagens',
					iconCls:'action',
					route: 'Home/timeline'
				},
				{
					text:'Inserir Mensagem',
					iconCls:'compose',
					route:'Home/newmessage'
				},
				{
					text:'Gerir Favoritos',
					iconCls:'favorites',
					route:'Home/favourites'
				},
				{
					text:'Gerir Preferências',
					iconCls:'user',
					route:'Home/preferences'
				},
				{
					text:'Instruções',
					iconCls:'info',
					route:'Home/instructions'
				}
			],
		},
	],
});