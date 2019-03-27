new Vue({
    el: '#title1',
    data: {
        Title_text: 'Lab. 4, var. 28705'
    }
});

new Vue({
    el: '#title2',
    data: {
        Title_text: 'Zhetesova Dana, p 3217'
    }
});

let form = new Vue({
    el: '#forms',
    data: {
        loginChoice: true,
        registerChoice: false,
        username: "",
        password: "",
        errorNameLogin: false,
        errorPasswordLogin: false,
        errorNameRegister: false,
        errorPasswordRegister: false
    },
    methods: {
        showLogin: function () {
            this.loginChoice = true;
            this.registerChoice = false;
            this.errorNameLogin = false;
            this.errorPasswordLogin = false;
            this.errorNameRegister = false;
            this.errorPasswordRegister = false;
            this.password = "";
        },
        showRegister: function () {
            this.loginChoice = false;
            this.registerChoice = true;
            this.errorNameLogin = false;
            this.errorPasswordLogin = false;
            this.errorNameRegister = false;
            this.errorPasswordRegister = false;
            this.password = "";
        },
        submitLogin: function () {
            doRequest("login");
        },
        submitRegister: function () {
            doRequest("register");
        }
    }
});
