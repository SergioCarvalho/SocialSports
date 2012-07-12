SocialSports.views.HashPlayersList = Ext.extend(Ext.Panel, {
   
	initComponent: function(){
        var cancelButton, addButton, titlebar, list;
		
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
		
        titlebar = {
            dock: 'top',
            xtype: 'toolbar',
            title: 'Hashtags de Jogadores',
            items: [ cancelButton, {xtype: 'spacer'}, addButton ]
        };

        list = {
			styleHtmlContent: true,
			style: 'background: #d8e2ef',
			cls: 'bt_selectfield',
            xtype: 'list',
            itemTpl: '{hashtag}',
            store: SocialSports.stores.HashPlayersStore,
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

        SocialSports.views.HashPlayersList.superclass.initComponent.call(this);
    },

    onAddAction: function() {
        Ext.dispatch({
            controller: 'HashPlayersController',
            action: 'newHashPlayer'
        });
    },

    onItemtapAction: function(list, index, item, e) {
        Ext.dispatch({
            controller: 'HashPlayersController',
            action: 'editHashPlayer',
            index: index
        });
    },
	
	onCancelAction: function() {
        Ext.dispatch({
            controller: 'PlayersController',
            action: 'indexPlayer',
        });
    }
});

Ext.reg('SocialSports.views.HashPlayersList', SocialSports.views.HashPlayersList);
