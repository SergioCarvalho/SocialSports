Ext.regController('NewMessagesController', {
	store: SocialSports.stores.NewMessagesStore,
	
    save: function(params) {
        params.record.set(params.data);
        var errors = params.record.validate();

        if (errors.isValid()) {
            this.store.create(params.data);
			Ext.Msg.alert('Sucesso', 'A sua mensagem foi enviada!');
			Ext.getCmp('messagetabpanel').resetForm();
			Ext.dispatch({
				controller:'Home',
				action: 'newmessage',
			});
        } 
    }
});