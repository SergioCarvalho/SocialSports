SocialSports.views.EventsList = Ext.extend(Ext.Panel, {
   
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
			text:'Hashtags Eventos',
			ui:'confirm',
			cls: 'bt_hashtags',
			handler: this.onHashAction,
			scope: this
		};
		
        titlebar = {
            dock: 'top',
            xtype: 'toolbar',
            title: 'Eventos',
            items: [ cancelButton, hashButton, { xtype: 'spacer' }, addButton]
        };

        list = {
			styleHtmlContent: true,
			style: 'background: #d8e2ef',
			cls: 'bt_selectfield',
            xtype: 'list',
            itemTpl: '{name}',
            store: SocialSports.stores.EventsStore,
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

        SocialSports.views.EventsList.superclass.initComponent.call(this);
    },

	onHashAction: function() {
		Ext.dispatch({
			controller: 'HashEventsController',
			action: 'indexHashEvent',
		});
	},
	
    onAddAction: function() {
        Ext.dispatch({
            controller: 'EventsController',
            action: 'newEvent',
        });
    },

    onItemtapAction: function(list, index, item, e) {
        Ext.dispatch({
            controller: 'EventsController',
            action: 'editEvent',
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

Ext.reg('SocialSports.views.EventsList', SocialSports.views.EventsList);
