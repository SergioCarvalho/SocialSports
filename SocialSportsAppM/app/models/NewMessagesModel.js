SocialSports.models.NewMessagesModel = Ext.regModel('NewMessagesModel', {
    fields: [
        {
            name: 'id',
            type: 'int'
        },  {
			name: 'hashtag',
			type: 'string'
		}, {
			name: 'mensagem',
			type: 'string'
		}, {
            name: 'lingua',
            type: 'string'
        },
    ],

    validations: [
        {
            type: 'presence',
            name: 'lingua'
        }, 
		
		{
			type: 'presence',
			name: 'hashtag'
		},
		{
			type: 'presence',
			name: 'mensagem',
			message: 'O campo da mensagem não pode estar vazio'
		}
    ],

    proxy: {
        type: 'localstorage',
        id: 'newmessages-lista'
    }
});
