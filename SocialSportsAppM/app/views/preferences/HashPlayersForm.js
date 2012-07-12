SocialSports.views.HashPlayersForm = Ext.extend(Ext.form.FormPanel, {
    defaultInstructions: 'Gestão de jogadores',

    initComponent: function(){
        var titlebar, cancelButton, buttonbar, saveButton, deleteButton, fields;

        cancelButton = {
            text: 'cancel',
            ui: 'back',
            handler: this.onCancelAction,
            scope: this
        };

        titlebar = {
            id: 'hashPlayersFormTitlebar',
            xtype: 'toolbar',
            title: 'Inserir Hashtag de Jogador',
            items: [ cancelButton ]
        };

        saveButton = {
            id: 'hashPlayersFormSaveButton',
            text: 'Gravar',
            ui: 'confirm',
            handler: this.onSaveAction,
            scope: this
        };

        deleteButton = {
            id: 'hashPlayersFormDeleteButton',
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
            id: 'hashPlayersFormFieldset',
            title: 'Detalhes da hashtag de jogador',
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
                    name: 'jogador',
                    label: 'Jogador',
					store: SocialSports.stores.PlayersStore,
					displayField: 'name',
					valueField: 'name'
                },
                {
                    xtype: 'SocialSports.views.ErrorField',
                    fieldname: 'jogador',
                },
            ]
        };

        Ext.apply(this, {
            scroll: 'vertical',
            dockedItems: [ titlebar, buttonbar ],
            items: [ fields ],
            listeners: {
                beforeactivate: function() {
                    var deleteButton = this.down('#hashPlayersFormDeleteButton'),
                        saveButton = this.down('#hashPlayersFormSaveButton'),
                        titlebar = this.down('#hashPlayersFormTitlebar'),
                        model = this.getRecord();

                    if (model.phantom) {
                        titlebar.setTitle('Inserir hashtag de jogador');
                        saveButton.setText('Gravar');
                        deleteButton.hide();
                    } else {
                        titlebar.setTitle('Hashtag Jogador (detalhes)');
                        saveButton.setText('Actualizar');
                        deleteButton.show();
                    }
                },
                deactivate: function() { this.resetForm() }
            }
        });

        SocialSports.views.HashPlayersForm.superclass.initComponent.call(this);
    },

    onCancelAction: function() {
        Ext.dispatch({
            controller: 'HashPlayersController',
            action: 'indexHashPlayer',
        });
    },

    onSaveAction: function() {
        var model = this.getRecord();

        Ext.dispatch({
            controller: 'HashPlayersController',
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
                    controller: 'HashPlayersController',
                    action    : 'remove',
                    record    : this.getRecord()
                });
            }
        }, this);
    },

    showErrors: function(errors) {
        var fieldset = this.down('#hashPlayersFormFieldset');
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
        var fieldset = this.down('#hashPlayersFormFieldset');
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

Ext.reg('SocialSports.views.HashPlayersForm', SocialSports.views.HashPlayersForm);
