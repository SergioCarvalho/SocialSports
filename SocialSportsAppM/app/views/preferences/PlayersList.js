SocialSports.views.PlayersList = Ext.extend(Ext.Panel, {
   
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
			text:'Hashtags Jogadores',
			ui:'confirm',
			cls: 'bt_hashtags',
			handler: this.onHashAction,
			scope: this
		};
		
        titlebar = {
            dock: 'top',
            xtype: 'toolbar',
            title: 'Jogadores',
            items: [ cancelButton, hashButton, { xtype: 'spacer' }, addButton]
        };

        list = {
			styleHtmlContent: true,
			style: 'background: #d8e2ef',
			cls: 'bt_selectfield',
            xtype: 'list',
            itemTpl: '{name}',
            store: SocialSports.stores.PlayersStore,
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

        SocialSports.views.PlayersList.superclass.initComponent.call(this);
    },

	onHashAction: function() {
		Ext.dispatch({
			controller: 'HashPlayersController',
			action: 'indexHashPlayer',
		});
	},
	
    onAddAction: function() {
        Ext.dispatch({
            controller: 'PlayersController',
            action: 'newPlayer',
        });
    },

    onItemtapAction: function(list, index, item, e) {
        Ext.dispatch({
            controller: 'PlayersController',
            action: 'editPlayer',
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

Ext.reg('SocialSports.views.PlayersList', SocialSports.views.PlayersList);
