Ext.regController('PreferencesController', {
	store: SocialSports.stores.PreferencesStore,
	
	//function save
    save: function(params) {
        params.record.set(params.data);
        var errors = params.record.validate();

        if (errors.isValid()) {
			//aqui antes de criar vou remover a entrada anteriormente criada porque só queremos ter uma preferência
            this.store.remove(this.store.getAt(0));
			this.store.sync();
			this.store.create(params.data);
			
			Ext.Msg.alert('Preferências guardadas!', 'As suas preferências foram guardadas. Quando regressar à página serão exibidas as preferências por defeito');
			Ext.getCmp('preferencesform').resetForm();
             
			Ext.dispatch({
				controller:'Home',
				action: 'preferences',
			});
        } else {
            params.form.showErrors(errors);
        }
    },

});