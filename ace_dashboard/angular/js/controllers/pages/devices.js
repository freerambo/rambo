angular.module('AceApp').controller('DevicesCtrl', function($scope, $rootScope,$location, $timeout,$http, $filter, $localStorage) {


	 dcload = 0.0;
	 // acload = 0.0;
	 dcsource = 0.0;
	 acsource = 0.0;



	var ws = new WebSocket("ws://172.21.76.225:8998/pm");

	ws.onopen = function(){
		console.log("Socket has been opened!");
	};

	ws.onmessage = function(message) {

		$scope.$apply(function () {
			listener(JSON.parse(message.data));
		});

	};

	ws.onclose = function(){
		//try to reconnect in 3 seconds
		setTimeout(function(){start(websocketServerLocation)}, 3000);
	};

	// $http.get("data/pages/devices/meters.json").success(function(data) {
	function listener(data) {

		// $scope.meters  = data;
		$scope.dcmeters = data.DC;
		$scope.acmeters = data.AC;
		$scope.pvs = [];
        $scope.bics = [];
        $scope.batteries = [];
        $scope.dcloads = [];
        $scope.dcsources = [];
        $scope.acloads = [];
        $scope.acsources = [];
		$scope.acsimulators = [];
		// console.log($scope.dcmeters);
		angular.forEach($scope.dcmeters, function(object, key) {
			// console.log(object);

			description = object.description.toUpperCase();
			// console.log(description);
            // console.log(JSON.stringify(object));
            // console.log();
			if(description.includes("PV")){
                $scope.pvs.push(object);
            } else if(description.includes("BATTERY")){
                $scope.batteries.push(object);
            } else if(description.includes("BIC")){
                $scope.bics.push(object);
            } else if(description.includes("DC SOURCE")){
                $scope.dcsources.push(object);
				dcsource = 	$filter('number')(object.power, 2);
            } else if(description.includes("DC LOAD")){
				dcload = 	$filter('number')(object.power, 2);

                $scope.dcloads.push(object);
            } else {
                console.warn("unexpected device type");
            }

		});

		angular.forEach($scope.acmeters, function(object, key) {
			description = object.description.toUpperCase();
			// console.log(description);
			// console.log(JSON.stringify(object));
			if(description.includes("AC LOAD")){
				$scope.acloads.push(object);
			} else if(description.includes("AC SOURCE")){
				$scope.acsources.push(object);
				acsource  = 	$filter('number')(object.power, 2);
				// acsource = 	$filter('number')(object.power, 2);
			} else if(description.includes("GRID SIMULATOR")){
				$scope.acsimulators.push(object);
			}else {
				console.warn("unexpected device type");
			}
		});
	}



	//we are hiding widgets and will show them only after they are re-arranged, etc ...
	$scope.contentLoaded = false;
	$scope.$on('$viewContentLoaded', function(){ 
		$timeout(function() {
			$scope.contentLoaded = true;
		}, 300);
	});


	//list of toolbar options for widget 1
    $scope.widget_toolbar_1 = {'reload': true, 'close': true, 'toggle': true, 'fullscreen': true};
	
	//scope properties for widget 1
	$scope.is_widget_reloading = false;//set it to true so that widget shows 'reloading' icon
	$scope.is_widget_hidden = false;
	$scope.is_widget_fullscreen = false;
	$scope.toggle_state = !$scope.is_widget_hidden;
	
	
	//scope properties for widget 2
	$scope.is_widget2_hidden = true;
	$scope.is_widget_reloading_2 = false;
	
	/**
	$scope.widget_2_hidden = !$scope.widget_2_toggle;	
	$timeout(function() {
		$scope.widget_2_toggle = true;
		$timeout(function() {
			$scope.widget_2_toggle = false;
		}, 3500);
	}, 3500);
	*/
	

	//the widget 1 toolbar's callback when 'reload' button is clicked
	$scope.widget_reload = function() {
		$timeout(function() {
			$scope.is_widget_reloading = false;
		}, 1500);
	}
	
	//the widget 3 toolbar's callback when 'reload' button is clicked
	$scope.widget_reload_2 = function() {
		$timeout(function() {
			$scope.is_widget_reloading_2 = false;
		}, 1500);
	}

	$scope.changPath = function(deviceId){

		// alert(deviceId);
		$location.path("/devicedetails/" + deviceId);

	}
	
	//the small (reload) button when clicked
	$scope.button_reload = function() {
		$scope.is_widget_reloading = true;
		$timeout(function() {
			$scope.is_widget_reloading = false;
		}, 750);
	}
	//the small (fullscreen) button when clicked
	$scope.button_fullscreen = function() {
		$scope.is_widget_fullscreen = !$scope.is_widget_fullscreen;	
	}
	//the small (toggle/eye) button when clicked
	$scope.button_toggle = function() {
		$scope.toggle_state = !$scope.toggle_state;
	}

	
	
	//widget 2's colorpicker options
	$scope.widgetColorpicker = {
		pull_right: true
	};	
	$scope.widgetColor = '#307ECC';
	$scope.widgetColorClass = '';
	
	$scope.widgetColors = {
		'#307ECC': 'blue', '#5090C1': 'blue2', '#6379AA': 'blue3',
		'#82AF6F': 'green', '#2E8965': 'green2', '#5FBC47': 'green3',
		'#E2755F': 'red', '#E04141': 'red2', '#D15B47': 'red3',
		'#FFC657': 'orange', '#7E6EB0': 'purple', '#CE6F9E': 'pink',
		'#404040': 'dark', '#848484': 'grey', '#EEEEEE': 'default'
	};



	//jQuery UI sortable options
	$scope.sortableOpts = {
		items:'> .widget-box',
		handle: ace.vars['touch'] ? '.widget-title' : false,
		cancel: '.fullscreen',
		opacity:0.8,
		revert:true,
		forceHelperSize:true,
		placeholder: 'widget-placeholder',
		forcePlaceholderSize:true,
		tolerance:'pointer',
		start: function(event, ui) {
			//when an element is moved, it's parent becomes empty with almost zero height.
			//we set a min-height for it to be large enough so that later we can easily drop elements back onto it
			//ui.item.parent().css({'min-height':ui.item.height()})
			//ui.sender.css({'min-height':ui.item.height() , 'background-color' : '#F5F5F5'})
		},
		update: function(event, ui) {
			//ui.item.parent({'min-height':''})
			//p.style.removeProperty('background-color');
		}
	};
	
	
	//reset localStorage
	$scope.resetStorage = function() {
		$localStorage.$reset();
		document.location.reload();
	};



	Highcharts.setOptions({
		global: {
			useUTC: false
		}
	});

	// Create the chart
	$('#container').highcharts('StockChart', {
		chart: {
			events: {
				load: function () {

					// set up the updating of the chart each second
					var series = this.series[0];

					var series1 = this.series[1];

					var series2 = this.series[2];
					var series3 = this.series[3];
					setInterval(function () {
						var x = (new Date()).getTime(), // current time
						// 	y = Math.round(Math.random() * 100);
						// y1 = Math.round(Math.random() * 100);

						y =  dcload* 100/100.0;
						y1 = dcsource * 100/100.0;
						y2 = acsource * 100/100.0;
							// y2 = Math.round(Math.random() * 100);

						// y3 = Math.round(Math.random() * 100);
						series.addPoint([x, y], true, true);
						series1.addPoint([x, y1], true, true);
						series2.addPoint([x, y2], true, true);
						// series3.addPoint([x, y3], true, true);

					}, 1000);
				}
			}
		},

		rangeSelector: {
			buttons: [{
				count: 1,
				type: 'minute',
				text: '1M'
			}, {
				count: 5,
				type: 'minute',
				text: '5M'
			}, {
				type: 'all',
				text: 'All'
			}],
			inputEnabled: false,
			selected: 0
		},

		title: {
			text: 'Devices Live Series Data'
		},

		exporting: {
			enabled: false
		},
		credits: {
			enabled: false
		},
		series: [{
			name: 'DC Load (Kw)',
			data: (function () {
				// generate an array of random data
				var data2 = [],
					time = (new Date()).getTime(),
					i;

				for (i = -100; i <= 0; i += 1) {
					data2.push([
						time + i * 1000,
						0.0// Math.round(Math.random() * 100)/100.0
					]);
				}


				return data2;
			}())
		},{
			name: 'DC Source (Kw)',
			data: (function () {
				// generate an array of random data
				var data1 = [],
					time = (new Date()).getTime(),
					i;

				for (i = -100; i <= 0; i += 1) {
					data1.push([
						time + i * 1000,
						0.0// Math.round(Math.random() * 100)/100.0
					]);
				}


				return data1;
			}())
		},{
			name: 'AC Source (Kw)',
			data: (function () {
				// generate an array of random data
				var data3 = [],
					time = (new Date()).getTime(),
					i;

				for (i = -100; i <= 0; i += 1) {
					data3.push([
						time + i * 1000,
						0.0// Math.round(Math.random() * 100)/100.0
					]);
				}


				return data3;
			}())
		}/*,{
			name: 'AC Load',
			data: (function () {
				// generate an array of random data
				var data4 = [],
					time = (new Date()).getTime(),
					i;

				for (i = -100; i <= 0; i += 1) {
					data4.push([
						time + i * 1000,
						Math.round(Math.random() * 100)
					]);
				}


				return data4;
			}())
		}*/
		]
	});


});
