var app = angular.module("dribbbleBookmark", ['ui.bootstrap']);

app.controller("dribbleController", function($scope, $http, $document, $element, $uibModal){
    $scope.shotViewSelected = true;
    $scope.page = 1;
    $scope.shots = [];
    $scope.bookmarks = [];
    $scope.retrieving = false;
    $scope.retrieveOnlyRecentBookmarks = false;
    $scope.retrieveShots = function(){
        $scope.retrieving = true;
        $http({
            url: "/shot",
            method: "GET",
            params: {
                page: $scope.page
            }
        }).then(function(response){
            $scope.shots = $scope.shots.concat(response.data);
            $scope.retrieving = false;
        });
    }
    $scope.openShotDetails = function(shot){
        var modalInstance = $uibModal.open({
          animation: true,
          ariaLabelledBy: 'modal-title-shot',
          ariaDescribedBy: 'modal-body-shot',
          templateUrl: 'shotDetailsModal.html',
          controller: 'ShotDetailsModalController',
          controllerAs: '$ctrl',
          resolve: {
            shot: function () {
              return shot;
            },
            scope: function(){
              return $scope;
            }
          }
        });
        modalInstance.result.then(function () {
          console.log("resolved");
        }, function () {
          console.log("dismissed");
        });
    };
    $scope.switchRetrieveOnly = function(retriveOnly){
        $scope.retrieveOnlyRecentBookmarks = retriveOnly;
        $scope.retrieveBookmarks();
    }
    $scope.openBookmarkDetails = function(bookmark){
        var modalInstance = $uibModal.open({
          animation: true,
          ariaLabelledBy: 'modal-title-bookmark',
          ariaDescribedBy: 'modal-body-bookmark',
          templateUrl: 'bookmarkDetailsModal.html',
          controller: 'BookmarkDetailsModalController',
          controllerAs: '$ctrl',
          resolve: {
            bookmark: function () {
              return bookmark;
            },
            scope: function(){
              return $scope;
            }
          }
        });
        modalInstance.result.then(function () {
          console.log("resolved");
        }, function () {
          console.log("dismissed");
        });
    };
    $scope.retrieveBookmarks = function(){
        $http({
            url: "/bookmark",
            method: "GET",
            params: {
                recentOnly: $scope.retrieveOnlyRecentBookmarks
            }
        }).then(function(response){
            $scope.bookmarks = response.data;
        });
    }
    $scope.loadMoreShots = function(){
        $scope.page++;
        $scope.retrieveShots();
    }
    $scope.retrieveShots();
    $scope.retrieveBookmarks();
    var scrollDiv = $document[0].getElementsByClassName("row-shots")[0];
    scrollDiv.onscroll = function(event){
        if(!$scope.retrieving && (scrollDiv.scrollTop >= (scrollDiv.scrollHeight - scrollDiv.offsetHeight) - 1000)){
            $scope.loadMoreShots();
        }
    };
});

app.controller('ShotDetailsModalController', function ($uibModalInstance, $http, shot, scope) {
  var $ctrl = this;
  $ctrl.showErrorMessage = false;
  $ctrl.showSuccessMessage = false;
  $ctrl.creationDate = new Date(shot.created_at);
  $ctrl.bookmark = function(){
    $ctrl.showErrorMessage = false;
    $ctrl.showSuccessMessage = false;
    $http({
        url: "/bookmark",
        method: "POST",
        data: {
            shotId: shot.id,
        }
    }).then(function(response){
        var message = response.data.message;
        if(message == "error"){
           $ctrl.showErrorMessage = true;
        } else{
           $ctrl.showSuccessMessage = true;
           scope.retrieveBookmarks();
        }
    }, function(error, status){
        alert(JSON.stringify(error));
    });
  };
  $ctrl.shot = shot;
  $ctrl.maximize = function(){
    window.open(shot.images.hidpi);
  }
});

app.controller('BookmarkDetailsModalController', function ($uibModalInstance, $http, bookmark, scope) {
  var $ctrl = this;
  $ctrl.showSuccessMessage = false;
  $ctrl.creationDate = new Date(bookmark.shot.created_at);
  $ctrl.bookmarkDate = new Date(bookmark.date);
  $ctrl.unbookmark = function(){
    $ctrl.showSuccessMessage = false;
    $http({
        url: "/bookmark/"+bookmark.shotId,
        method: "DELETE"
    }).then(function(response){
        var message = response.data.message;
        if(message == "success"){
           $ctrl.showSuccessMessage = true;
           scope.retrieveBookmarks();
           $uibModalInstance.dismiss('cancel');
        }
    }, function(error, status){
        alert(JSON.stringify(error));
    });
  };
  $ctrl.bookmark = bookmark;
  $ctrl.maximize = function(){
    window.open(bookmark.shot.images.hidpi);
  }
});