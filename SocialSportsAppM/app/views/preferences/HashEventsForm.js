SocialSports.views.HashEventsForm = Ext.extend(Ext.form.FormPanel, {
    defaultInstructions: 'Gestão de eventos',

    initComponent: function(){
        var titlebar, cancelButton, buttonbar, saveButton, deleteButton, fields;

        cancelButton = {
            text: 'cancel',
            ui: 'back',
            handler: this.onCancelAction,
            scope: this
        };

        titlebar = {
            id: 'hashEventsFormTitlebar',
            xtype: 'toolbar',
            title: 'Inserir Hashtag de Evento',
            items: [ cancelButton ]
        };

        saveButton = {
            id: 'hashEventsFormSaveButton',
            text: 'Gravar',
            ui: 'confirm',
            handler: this.onSaveAction,
            scope: this
        };

        deleteButton = {
            id: 'hashEventsFormDeleteButton',
            text: 'Apagar',
            ui: 'decline',
            handler: this.onDeleteAction,
            scope: this
        };

        buttonbar = {
            xtype: 'toolbar',
            dock: 'bottom',
            items: [deleteButton, {xtype: 'spacer'}, saveButton]
        };

        fields = {
            xtype: 'fieldset',
            id: 'hashEventsFormFieldset',
            title: 'Detalhes da hashtag de evento',
            instructions: this.defaultInstructions,
            defaults: {
                xtype: 'textfield',
                labelAlign: 'left',
                labelWidth: '30%',
                required: false,
                useClearIcon: true,
                autoCapitalize : false
            },
            items: [
                {	
					name : 'hashtag',
                    label: 'Hashtag',
					autoCapitalize : true
                },
                {
                    xtype: 'SocialSports.views.ErrorField',
                    fieldname: 'hashtag',
                },
				{
					xtype: 'selectfield',
                    name: 'modalidade',
                    label: 'Modalidade',
					options: [{
						text: 'Futebol',
						value: 'Futebol'
					},
					{
						text: 'Ténis',
						value: 'Ténis'
					}],
                    autoCapitalize : true
                },
                {
                    xtype: 'SocialSports.views.ErrorField',
                    fieldname: 'modalidade',
                },
				{
					xtype: 'selectfield',
                    name: 'evento',
                    label: 'Evento',
					store: SocialSports.stores.EventsStore,
					displayField: 'name',
					valueField: 'name'
                },
                {
                    xtype: 'SocialSports.views.ErrorField',
                    fieldname: 'evento',
                },
            ]
        };

        Ext.apply(this, {
            scroll: 'vertical',
            dockedItems: [ titlebar, buttonbar ],
            items: [ fields ],
            listeners: {
                beforeactivate: function() {
                    var deleteButton = this.down('#hashEventsFormDeleteButton'),
                        saveButton = this.down('#hashEventsFormSaveButton'),
                        titlebar = this.down('#hashEventsFormTitlebar'),
                        model = this.getRecord();

                    if (model.phantom) {
                        titlebar.setTitle('Inserir hashtag de evento');
                        saveButton.setText('Gravar');
                        deleteButton.hide();
                    } else {
                        titlebar.setTitle('Hashtag Evento (detalhes)');
                        saveButton.setText('Actualizar');
                        deleteButton.show();
                    }
                },
                deactivate: function() { this.resetForm() }
            }
        });

        SocialSports.views.HashEventsForm.superclass.initComponent.call(this);
    },

    onCancelAction: function() {
        Ext.dispatch({
            controller: 'HashEventsController',
            action: 'indexHashEvent',
        });
    },

    onSaveAction: function() {
        var model = this.getRecord();

        Ext.dispatch({
            controller: 'HashEventsController',
            action    : (model.phantom ? 'save' : 'update'),
            data      : this.getValues(),
            record    : model,
            form      : this
        });
    },

    onDeleteAction: function() {
        Ext.Msg.confirm("Apagar esta hashtag?", "", function(answer) {
            if (answer === "yes") {
                Ext.dispatch({
                    controller: 'HashEventsController',
                    action    : 'remove',
                    record    : this.getRecord()
                });
            }
        }, this);
    },

    showErrors: function(errors) {
        var fieldset = this.down('#hashEventsFormFieldset');
        this.fields.each(function(field) {
            var fieldErrors = errors.getByField(field.name);

            if (fieldErrors.length > 0) {
                var errorField = this.down('#'+field.name+'ErrorField');
                field.addCls('invalid-field');
                errorField.update(fieldErrors);
                errorField.show();
            } else {
                this.resetField(field);
            }
        }, this);
        fieldset.setInstructions("Por favor emende os campos assinalados");
    },

    resetForm: function() {
        var fieldset = this.down('#hashEventsFormFieldset');
        this.fields.each(function(field) {
            this.resetField(field);
        }, this); 
        fieldset.setInstructions(this.defaultInstructions);
        this.reset();
    },

    resetField: function(field) {
        var errorField = this.down('#'+field.name+'ErrorField');
        errorField.hide();
        field.removeCls('invalid-field');
        return errorField;
    }
});

Ext.reg('SocialSports.views.HashEventsForm', SocialSports.views.HashEventsForm);
