SocialSports.views.TeamsForm = Ext.extend(Ext.form.FormPanel, {
    defaultInstructions: 'Gestão de equipas',

    initComponent: function(){
        var titlebar, cancelButton, buttonbar, saveButton, deleteButton, fields;

        cancelButton = {
            text: 'cancel',
            ui: 'back',
            handler: this.onCancelAction,
            scope: this
        };

        titlebar = {
            id: 'teamsFormTitlebar',
            xtype: 'toolbar',
            title: 'Inserir Equipa',
            items: [ cancelButton ]
        };

        saveButton = {
            id: 'teamsFormSaveButton',
            text: 'Gravar',
            ui: 'confirm',
            handler: this.onSaveAction,
            scope: this
        };

        deleteButton = {
            id: 'teamsFormDeleteButton',
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
            id: 'teamsFormFieldset',
            title: 'Detalhes da equipa',
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
                    xtype: 'textfield',
					name : 'name',
                    label: 'Nome',
					autoCapitalize : true
                },
                {
                    xtype: 'SocialSports.views.ErrorField',
                    fieldname: 'name',
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
                }
            ]
        };

        Ext.apply(this, {
            scroll: 'vertical',
            dockedItems: [ titlebar, buttonbar ],
            items: [ fields ],
            listeners: {
                beforeactivate: function() {
                    var deleteButton = this.down('#teamsFormDeleteButton'),
                        saveButton = this.down('#teamsFormSaveButton'),
                        titlebar = this.down('#teamsFormTitlebar'),
                        model = this.getRecord();

                    if (model.phantom) {
                        titlebar.setTitle('Inserir equipa');
                        saveButton.setText('Gravar');
                        deleteButton.hide();
                    } else {
                        titlebar.setTitle('Equipa (detalhes)');
                        saveButton.setText('Actualizar');
                        deleteButton.show();
                    }
                },
                deactivate: function() { this.resetForm() }
            }
        });

        SocialSports.views.TeamsForm.superclass.initComponent.call(this);
    },

    onCancelAction: function() {
        Ext.dispatch({
            controller: 'TeamsController',
            action: 'indexTeam',
        });
    },

    onSaveAction: function() {
        var model = this.getRecord();

        Ext.dispatch({
            controller: 'TeamsController',
            action    : (model.phantom ? 'save' : 'update'),
            data      : this.getValues(),
            record    : model,
            form      : this
        });
    },

    onDeleteAction: function() {
        Ext.Msg.confirm("Apagar esta equipa?", "", function(answer) {
            if (answer === "yes") {
                Ext.dispatch({
                    controller: 'TeamsController',
                    action    : 'remove',
                    record    : this.getRecord()
                });
            }
        }, this);
    },

    showErrors: function(errors) {
        var fieldset = this.down('#teamsFormFieldset');
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
        var fieldset = this.down('#teamsFormFieldset');
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

Ext.reg('SocialSports.views.TeamsForm', SocialSports.views.TeamsForm);
