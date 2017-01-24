//Define a function scope, variables used inside it will NOT be globally visible.
(function () {

    var
            //the HTTP headers to be used by all requests
            httpHeaders,
            //the message to be shown to the user
            message,
            //Define the main module.
            //The module is accessible everywhere using "angular.module('angularspring')", therefore global variables can be avoided totally.
            as = angular.module('exampleApp', ['ngRoute', 'ngResource', 'ngCookies', 'ui.bootstrap', 'ngMessages', 'exampleApp.i18n', 'exampleApp.services', 'exampleApp.controllers', 'exampleApp.filters']);

    as.config(function ($routeProvider, $httpProvider) {
        //configure the rounting of ng-view
        $routeProvider
                .when('/',
                        {templateUrl: 'partials/home.html',
                            publicAccess: true})
                .when('/home',
                        {templateUrl: 'partials/home.html',
                            publicAccess: true})
                .when('/login',
                        {templateUrl: 'partials/login.html',
                            publicAccess: true})
                .when('/devices',
                        {controller: 'DevicesController',
                            templateUrl: 'partials/devices/list.html',
                            publicAccess: true})
                 .when('/devices/new',
                        {controller: 'NewDeviceController',
                            templateUrl: 'partials/devices/new.html',
                            publicAccess: true}) 
                 .when('/devices/update/:id',
                        {controller: 'UpdateDeviceController',
                        templateUrl: 'partials/devices/edit.html',
                        publicAccess: true})            
                 .when('/devices/:id',
                        {controller: 'DeviceDetailsController',
                            templateUrl: 'partials/devices/details.html',
                            publicAccess: true
                            })           
                            ;


        //configure $http to catch message responses and show them

//        httpHeaders = $httpProvider.defaults.headers;
//        $httpProvider.defaults.headers.common['Access-Control-Allow-Origin'] = '*';

    });


    as.run(function ($rootScope, $http, $route, $location, base64) {
        //make current message accessible to root scope and therefore all scopes
        $rootScope.message = function () {
            return message;
        };

        /**
         * Holds all the requests which failed due to 401 response.
         */
        $rootScope.requests401 = [];

        $rootScope.$on('event:loginRequired', function () {
            //$('#login').modal('show');
            $location.path('/login');
        });

        /**
         * On 'event:loginConfirmed', resend all the 401 requests.
         */
        $rootScope.$on('event:loginConfirmed', function () {
            var i,
                    requests = $rootScope.requests401,
                    retry = function (req) {
                        $http(req.config).then(function (response) {
                            req.deferred.resolve(response);
                        });
                    };

            for (i = 0; i < requests.length; i += 1) {
                retry(requests[i]);
            }

            $rootScope.requests401 = [];
            $location.path('/devices');
        });

        /**
         * On 'event:loginRequest' send credentials to the server.
         */
        $rootScope.$on('event:loginRequest', function (event, username, password) {
            httpHeaders.common['Authorization'] = 'Basic ' + base64.encode(username + ':' + password);
            console.log('httpHeaders.common[\'Authorization\']@' + httpHeaders.common['Authorization'] + ':::' + username + ':' + password);
            $http.get('api/me')
                    .success(function (data) {
                        $rootScope.authenticated = true;
                        $rootScope.name = data.username;
                        $rootScope.$broadcast('event:loginConfirmed');
                    })
                    .error(function (data) {
                        console.log('login failed...@' + data);
                    });
        });

        /**
         * On 'logoutRequest' invoke logout on the server and broadcast 'event:loginRequired'.
         */
     

        //$rootScope.$on('$viewContentChange', funtion());
        //check the networking connection.

        $http.get('api/ping')
                .success(function (data) {
                    console.log("ping result@"+data);
                })
                .error(function (data) {
                     $rootScope.message={text:'Network connection eror!', type:'danger', show:true};
                });
    });

}());