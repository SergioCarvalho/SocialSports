SocialSports.views.HashEventsList = Ext.extend(Ext.Panel, {
   
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
            title: 'Hashtags de Eventos',
            items: [ cancelButton, {xtype: 'spacer'}, addButton ]
        };

        list = {
			styleHtmlContent: true,
			style: 'background: #d8e2ef',
			cls: 'bt_selectfield',
            xtype: 'list',
            itemTpl: '{hashtag}',
            store: SocialSports.stores.HashEventsStore,
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

        SocialSports.views.HashEventsList.superclass.initComponent.call(this);
    },

    onAddAction: function() {
        Ext.dispatch({
            controller: 'HashEventsController',
            action: 'newHashEvent'
        });
    },

    onItemtapAction: function(list, index, item, e) {
        Ext.dispatch({
            controller: 'HashEventsController',
            action: 'editHashEvent',
            index: index
        });
    },
	
	onCancelAction: function() {
        Ext.dispatch({
            controller: 'EventsController',
            action: 'indexEvent',
        });
    }
});

Ext.reg('SocialSports.views.HashEventsList', SocialSports.views.HashEventsList);
