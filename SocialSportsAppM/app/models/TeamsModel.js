SocialSports.models.TeamsModel = Ext.regModel('TeamsModel', {
    fields: [
        {
            name: 'id',
            type: 'int'
        }, {
            name: 'name',
            type: 'string'
        }, {
			name: 'modalidade',
			type: 'string'
		}
    ],

    validations: [
        {
            type: 'presence',
            name: 'name',
			message: 'Deve introduzir uma equipa antes de tentar gravá-la'
        }, 
		
		{
			type: 'presence',
			name: 'modalidade'
		}
    ],

    proxy: {
        type: 'localstorage',
        id: 'equipa-lista'
    }
});
