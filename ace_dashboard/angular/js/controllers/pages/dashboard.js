function getRandomInt(min, max) {
	min = Math.ceil(min);
	max = Math.floor(max);
	return Math.floor(Math.random() * (max - min)) + min;
}


angular.module('AceApp').controller('DashboardCtrl', function($scope, $http, $timeout, $rootScope) {
  
  $scope.alert = {
	'shown' : true,
	'close' : function() {
		$scope.alert.shown = false;
	}
  }

  //flot chart options and data
  $scope.piechart = {
   'data' : [
	 { label: "Solar PV 1",  data: 38.7, color: "#68BC31"},
	 { label: "DC source 1",  data: 24.5, color: "#2091CF"},
	 { label: "AC Source",  data: 8.2, color: "#AF4E96"},
	 { label: "Battery 1",  data: 18.6, color: "#DA5430"},
	 { label: "other",  data: 10, color: "#FEE074"}
   ],

   'options' : {
	series: {
		pie: {
			show: true,
			tilt:0.8,
			highlight: {
				opacity: 0.25
			},
			stroke: {
				color: '#fff',
				width: 2
			},
			startAngle: 2
		}
	},
	legend: {
		show: true,
		position: "ne", 
		labelBoxBorderColor: null,
		margin:[-30,15]
	}
	,
	grid: {
		hoverable: true,
		clickable: true
	}
  }
 };




 //data for second flot chart
 var d1 = [];

 for (var i = 0; i < 24; i++) {
	d1.push([i, getRandomInt(100,150)]);
 }
 var d2 = [];
	for (var i = 0; i < 24; i++) {
		d2.push([i, getRandomInt(140,190)]);
 }
 var d3 = [];
	for (var i = 0; i < 24; i++) {
		d3.push([i, getRandomInt(80,130)]);
 }
 $scope.saleschart = {
	'data' : [
		{ label: "Dc Load 1", data: d1 },
		{ label: "Dc Load 2", data: d2 },
		{ label: "Ac Load 1", data: d3 }

	],
	'options' : {
		hoverable: true,
		shadowSize: 0,
		series: {
			lines: { show: true },
			points: { show: true }
		},
		xaxis: {
			tickLength: 0
		},
		yaxis: {
			ticks: 10,
			min: 50,
			max: 200,
			tickDecimals: 2
		},
		grid: {
			backgroundColor: { colors: [ "#fff", "#fff" ] },
			borderWidth: 1,
			borderColor:'#555'
		}
	} 
 }

 
  
	//easy pie chart options
	//values are dynamically loaded, and are inside data/pages/dashboard
	$scope.easypiechart = {
		"energy-used": {
			barColor: '#3983C2',
			trackColor: '#E2E2E2',
			scaleColor: false,
			lineCap: 'butt',
			lineWidth: 4,
			size: 50
		}
		,
		"task-completion": {
			barColor: 'rgba(255,255,255,0.95)',
			trackColor: 'rgba(255,255,255,0.25)',
			scaleColor: false,
			lineCap: 'butt',
			lineWidth: 3,
			size: 39
		}
		,
		"customer-support": {
			barColor: '#ECCB71',
			trackColor: '#E2E2E2',
			scaleColor: false,
			lineCap: 'butt',
			lineWidth: 3,
			size: 30
		}
	};
	
	
	//sparkline options
	//values are dynamically loaded, and are inside data/pages/dashboard
	$scope.sparkline = {
/*		"page-views": {
			type: 'bar',
			barColor: '#f79263',
			chartRangeMin: 0,
			height: 24
		},*/
		"dc-grid": {
			type: 'bar',
			barColor: 'blue',
			chartRangeMin: 0,
			height: 24
		},
		"ac-grid": {
			type: 'bar',
			barColor: '#f79263',
			chartRangeMin: 0,
			height: 24
		},
		"earnings": {
			type: 'bar',
			barColor: '#FFF' ,
			chartRangeMin: 0,
			height: 18
		}
	};

	
	
	
	
 
 /////
 //the recent tab
 //in Bootstrap we can have tab buttons in one place and tab contents(panes) in another place
 //but seems not the case in Angular UI Bootstrap tabs
 //so we have 2 tabsets and then we hide contents of first one, and tab buttons of second one and correlate them using 'activeTab and 'setActiveTab'
 
  $scope.tabShown = false;
  angular.element('#recent-tab-list').scope().$on('$viewContentLoaded', function () {
	$timeout(function() {
		jQuery('#recent-tab-list > .tab-content').unwrap().remove();
	});
  });

  angular.element('#recent-tab-pane').scope().$on('$viewContentLoaded', function () {
	$timeout(function() {
		jQuery('#recent-tab-pane > .nav-tabs').hide();
		jQuery('#recent-tab-pane > .tab-content').addClass('padding-8');
		
		$scope.tabShown = true;
	});
  });

  $scope.activeTab = 0;
  $scope.setActiveTab = function(index) {
	 $scope.activeTab = index;
  }
 ////
	
});