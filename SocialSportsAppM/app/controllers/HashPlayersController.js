Ext.regController('HashPlayersController', {
	store: SocialSports.stores.HashPlayersStore,
	
	//function indexHashPlayer
	indexHashPlayer: function()
	{
        SocialSports.views.viewport.reveal('hashPlayersList');
	},

	newHashPlayer: function() {
        var model = new SocialSports.models.HashPlayersModel();
		SocialSports.views.hashPlayersForm.load(model);
        SocialSports.views.viewport.reveal('hashPlayersForm');
    },
	
    editHashPlayer: function(params) {
        var model = this.store.getAt(params.index);
		SocialSports.views.hashPlayersForm.load(model);
        SocialSports.views.viewport.reveal('hashPlayersForm');
    },

    save: function(params) {
        params.record.set(params.data);
        var errors = params.record.validate();

        if (errors.isValid()) {
            this.store.create(params.data);
            this.indexHashPlayer();
        } else {
            params.form.showErrors(errors);
        }
    },

    update: function(params) {
        var tmpHashPlayer = new SocialSports.models.HashPlayersModel(params.data),
            errors = tmpHashPlayer.validate()

        if (errors.isValid()) {
            /*params.record.set(params.data);
            params.record.save();
            this.indexTeam();*/
			this.store.remove(params.record);
			this.store.sync();
			this.store.create(params.data);
			this.store.sync();
            this.indexHashPlayer();
        } else {
            params.form.showErrors(errors);
        }
    },

    remove: function(params) {
        this.store.remove(params.record);
        this.store.sync();
        this.indexHashPlayer();
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