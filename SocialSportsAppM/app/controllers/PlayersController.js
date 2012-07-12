Ext.regController('PlayersController', {
	store: SocialSports.stores.PlayersStore,
	
	//function indexPlayer
	indexPlayer: function()
	{
        SocialSports.views.viewport.reveal('playersList');
	},

	newPlayer: function() {
        var model = new SocialSports.models.PlayersModel();
		SocialSports.views.playersForm.load(model);
        SocialSports.views.viewport.reveal('playersForm');
    },
	
    editPlayer: function(params) {
        var model = this.store.getAt(params.index);
		SocialSports.views.playersForm.load(model);
        SocialSports.views.viewport.reveal('playersForm');
    },

    save: function(params) {
        params.record.set(params.data);
        var errors = params.record.validate();

        if (errors.isValid()) {
            this.store.create(params.data);
            this.indexPlayer();
        } else {
            params.form.showErrors(errors);
        }
    },

    update: function(params) {
        var tmphashPlayer = new SocialSports.models.PlayersModel(params.data),
            errors = tmphashPlayer.validate()

        if (errors.isValid()) {
            /*params.record.set(params.data);
            params.record.save();
            this.indexTeam();*/
			this.store.remove(params.record);
			this.store.sync();
			this.store.create(params.data);
			this.store.sync();
            this.indexPlayer();
        } else {
            params.form.showErrors(errors);
        }
    },

    remove: function(params) {
        this.store.remove(params.record);
        this.store.sync();
        this.indexPlayer();
    }
/*	about: function()
	{
		if( ! this.aboutView)
		{
			this.aboutView = this.render({
				xtype: 'HomeAbout',
			});	
		}
		this.application.viewport.setActiveItem(this.aboutView);
		
		var backButton = this.application.viewport.query('#backBtn')[0];
		
		backButton.show();
		
		backButton.setHandler(function()
		{
				Ext.dispatch({
					controller: 'Home',
					action: 'timeline',
					historyUrl: 'Home/timeline',
					animation:{
						type:'slide',
						reverse: true,
					},
				});
		});
	},*/
});