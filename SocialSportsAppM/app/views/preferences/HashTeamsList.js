SocialSports.views.HashTeamsList = Ext.extend(Ext.Panel, {
   
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
            title: 'Hashtags de Equipas',
            items: [ cancelButton, {xtype: 'spacer'}, addButton ]
        };

        list = {
			styleHtmlContent: true,
			style: 'background: #d8e2ef',
			cls: 'bt_selectfield',
            xtype: 'list',
            itemTpl: '{hashtag}',
            store: SocialSports.stores.HashTeamsStore,
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

        SocialSports.views.HashTeamsList.superclass.initComponent.call(this);
    },

    onAddAction: function() {
        Ext.dispatch({
            controller: 'HashTeamsController',
            action: 'newHashTeam'
        });
    },

    onItemtapAction: function(list, index, item, e) {
        Ext.dispatch({
            controller: 'HashTeamsController',
            action: 'editHashTeam',
            index: index
        });
    },
	
	onCancelAction: function() {
        Ext.dispatch({
            controller: 'TeamsController',
            action: 'indexTeam',
        });
    }
});

Ext.reg('SocialSports.views.HashTeamsList', SocialSports.views.HashTeamsList);
