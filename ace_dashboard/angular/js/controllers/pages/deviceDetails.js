angular.module('AceApp').controller('DeviceDetailsCtrl', ['$scope','$rootScope','$filter','$stateParams','$http','$location',
    function($scope,$rootScope,$filter,$stateParams,$http,$location) {

    deviceId = $stateParams.deviceId;
        console.log(deviceId);

/*        $http({method: 'GET', url: "http://172.21.76.225:8999/get/" + deviceId,
 headers:{
 'Access-Control-Allow-Origin': '*',
 'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
 'Access-Control-Allow-Headers': 'Content-Type, X-Requested-With'
 }})*/

    if(deviceId.includes("ACSource") || deviceId.includes("DCSource") || deviceId.includes("DCLoad")){

        $http.get("http://172.21.76.225:8999/get/" + deviceId)
        .success(function(data) {
            $scope.info = {};
            $.each(data.data, function(i, val) {

                if (i === "variables") {
                    $scope.variables = val;

                } else {

                    if(i === "updatedTimestamp" ){
                        $scope.info[i] = $filter('date')(val,'yyyy-MM-dd HH:mm:ss');
                    }else
                        $scope.info[i] = val;
                }


            });

            // console.log($scope.info);
            // console.log($scope.variables);


            if (deviceId.includes("DCLoad")) {
                $scope.settings = [
                    {
                        "name":  "status" ,
                        "type": "radio",
                        "data" : [
                            ["on", "on"],
                            ["off", "off"]
                        ]
                    },
                    {
                        "name" :"mode",
                        "type": "radio",
                        "data" : [
                            ["0", "CCL"],
                            ["1", "CCH"],
                            ["2", "CRL"],
                            ["3", "CRH"],
                            ["4", "CVL"],
                            ["5", "CVH"],
                            ["6", "CPL"],
                            ["7", "CPH"]
                        ]
                    },
                    {
                        "name" :"channel",
                        "type": "radio",
                        "data" : [
                            ["channelA", "channelA"],
                            ["channelB", "channelB"]
                        ]
                    },
                    {
                        "name" :"inputValue",
                        "type": "text",
                        "placeholder": "Please input a value ",
                        "value": "50"
                    }];
            } else if (deviceId.includes("ACSource") || deviceId.includes("DCSource")) {
                $scope.settings = [
                    {
                        "name" :"overVoltageProtection",
                        "type": "text",
                        "placeholder": "Please input a value ",
                        "value": "450"
                    },
                    {
                        "name" :"overCurrentProtection",
                        "type": "text",
                        "placeholder": "Please input a value ",
                        "value": "20"
                    },
                    {
                        "name" :"voltage",
                        "type": "text",
                        "placeholder": "Please input a value ",
                        "value": "380"
                    },
                    {
                        "name" :"current",
                        "type": "text",
                        "placeholder": "Please input a value ",
                        "value": "10"
                    }

                ];
            }

        });
    }else {
        $http.get("data/pages/devicedetails/devicedetails.json").success(function(data) {
            $scope.info = {};
            $.each(data.data, function(i, val) {

                if (i === "variables") {
                    $scope.variables = val;

                } else if (i === "settings") {
                    $scope.settings = val;

                }else{
                    if(i === "updatedTimestamp" ){
                        $scope.info[i] = $filter('date')(val,'yyyy-MM-dd HH:mm:ss');
                    }else
                        $scope.info[i] = val;
                }


            });

            // console.log($scope.info);
            // console.log($scope.variables);
        });
    }
    // $scope.device = $rootScope.getData("dashboard");

        // $scope.device = ;
    // angular.forEach($rootScope.getData('domains'), function(domain, key) {
    //     domain.selected = newValue;
    // });

        $scope.updateRadioChoices = function(field, choice) {
            $.each(field.choices, function(i, val) {
                if (val.id != choice.id) val.selected = false;
            });
        }

    $scope.openDetail = function() {
        console.log("opendetails");
        $location.path('/devicedetails');
    }


}]);
