SocialSports.models.HashTeamsModel = Ext.regModel('HashTeamsModel', {
  fields: [
        {
            name: 'id',
            type: 'int'
        }, {
            name: 'hashtag',
            type: 'string',
        },  {
            name: 'equipa',
            type: 'string'
        },
		{
            name: 'modalidade',
            type: 'string'
        }
    ],

    validations: [
        {
            type: 'format',
            name: 'hashtag',
			message: 'Deve introduzir uma hashtag válida. Ex. #hashtag',
			matcher: /^#+[a-zA-Z0-9]*$/		//este matcher significa: símbolo ^ -> início de string; símbolo $ -> fim de string
        }, 									//#+ -> início tem de ter o catacter #; [a-zA-Z0-9]* -> * significa 1 ou + ocorrências
		{
            type: 'presence',
            name: 'modalidade'
        },
		{
            type: 'presence',
            name: 'equipa'
        }, 
    ],

    proxy: {
        type: 'localstorage',
        id: 'hashequipa-lista'
    }
});
