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

new Vue({
    el: '#logoutA',
    methods: {
        logout: function(){ logoutRequest(); }
    }
});

let form = new Vue({
    el: '#input',
    data: {
        x: 0,
        y: 0,
        r: 0
    }
});


let resultsTable = new Vue({
    el: '#result',
    data: {
        havePoins: false,
        resultData: []
    }
});
