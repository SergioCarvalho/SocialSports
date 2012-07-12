Ext.regController('PreferencesController', {
	store: SocialSports.stores.PreferencesStore,
	
	//function save
    save: function(params) {
        params.record.set(params.data);
        var errors = params.record.validate();

        if (errors.isValid()) {
			//aqui antes de criar vou remover a entrada anteriormente criada porque s� queremos ter uma prefer�ncia
            this.store.remove(this.store.getAt(0));
			this.store.sync();
			this.store.create(params.data);
			
			Ext.Msg.alert('Prefer�ncias guardadas!', 'As suas prefer�ncias foram guardadas. Quando regressar � p�gina ser�o exibidas as prefer�ncias por defeito');
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