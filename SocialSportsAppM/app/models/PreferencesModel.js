SocialSports.models.PreferencesModel = Ext.regModel('PreferencesModel', {
    fields: [
        {
            name: 'id',
            type: 'int'
        }, {
            name: 'tipoinformacao',
            type: 'string'
        }, {
			name: 'lingua',
			type: 'string'
		}, {
			name: 'fontesinformacao',
			type: 'string'
		},
    ],

    validations: [
        {
            type: 'presence',
            name: 'tipoinformacao'
        }, 
		
		{
			type: 'presence',
			name: 'lingua'
		},
		{
			type: 'presence',
			name: 'fontesinformacao'
		}
    ],

    proxy: {
        type: 'localstorage',
        id: 'preferences-lista'
    }
});
