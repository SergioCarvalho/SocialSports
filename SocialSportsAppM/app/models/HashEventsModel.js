SocialSports.models.HashEventsModel = Ext.regModel('HashEventsModel', {
  fields: [
        {
            name: 'id',
            type: 'int'
        }, {
            name: 'hashtag',
            type: 'string',
        },  {
            name: 'evento',
            type: 'string'
        },
		{
            name: 'modalidade',
            type: 'string'
        },
    ],

    validations: [
        {
            type: 'format',
            name: 'hashtag',
			message: 'Deve introduzir uma hashtag v�lida. Ex. #hashtag',
			matcher: /^#+[a-zA-Z0-9]*$/		//este matcher significa: s�mbolo ^ -> in�cio de string; s�mbolo $ -> fim de string
        }, 									//#+ -> in�cio tem de ter o catacter #; [a-zA-Z0-9]* -> * significa 1 ou + ocorr�ncias
		{
            type: 'presence',
            name: 'modalidade'
        },
		{
            type: 'presence',
            name: 'evento'
        }, 
    ],

    proxy: {
        type: 'localstorage',
        id: 'hashevento-lista'
    }
});
