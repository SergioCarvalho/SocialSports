SocialSports.views.TeamsList = Ext.extend(Ext.Panel, {
   
	initComponent: function(){
        var cancelButton, addButton, hashbutton, titlebar, list;
		
		cancelButton = {
            itemId: 'cancelButton',
            iconCls: 'arrow_left',
            iconMask: true,
            ui: 'plain',
            handler: this.onCancelAction,
            scope: this
        };
		
		addButton = {
            itemId: 'addButton',
            iconCls: 'add',
            iconMask: true,
            ui: 'plain',
            handler: this.onAddAction,
            scope: this
        };
		
		hashButton = {
			itemId:'hashButton',
			text:'Hashtags Equipas',
			ui:'confirm',
			cls: 'bt_hashtags',
			handler: this.onHashAction,
			scope: this
		};
		
        titlebar = {
            dock: 'top',
            xtype: 'toolbar',
            title: 'Equipas',
            items: [ cancelButton, hashButton, { xtype: 'spacer' }, addButton]
        };

        list = {
			styleHtmlContent: true,
			style: 'background: #d8e2ef',
			cls: 'bt_selectfield',
            xtype: 'list',
            itemTpl: '{name}',
            store: SocialSports.stores.TeamsStore,
            listeners: {
                scope: this,
                itemtap: this.onItemtapAction
            }
        };

        Ext.apply(this, {
           // html: 'placeholder',
            layout: 'fit',
            dockedItems: [titlebar],
            items: [list]
        });

        SocialSports.views.TeamsList.superclass.initComponent.call(this);
    },

	onHashAction: function() {
		Ext.dispatch({
			controller: 'HashTeamsController',
			action: 'indexHashTeam',
		});
	},
	
    onAddAction: function() {
        Ext.dispatch({
            controller: 'TeamsController',
            action: 'newTeam',
        });
    },

    onItemtapAction: function(list, index, item, e) {
        Ext.dispatch({
            controller: 'TeamsController',
            action: 'editTeam',
            index: index
        });
    },
	
	onCancelAction: function() {
        Ext.dispatch({
            controller: 'Home',
            action: 'favourites',
        });
    },
});

Ext.reg('SocialSports.views.TeamsList', SocialSports.views.TeamsList);
