angular.module('app', []).controller('bygroupController', function ($scope, $http) {
    const contextpath = '/3cad';

    $scope.init = function () {
        $scope.show();
    };

    $scope.show = function () {
        $scope.linkpdf = '';
        $http.get(contextpath + '/api/v1/activations/group')
            .then(function (response) {
                $scope.activations = response.data;
                $scope.count = 0;
                for (let i = 0; i < $scope.activations.length; i++) {
                    $scope.count += $scope.activations[i].count;
                }
                var now = new Date($scope.activations[0].dateDownloadNew);
                var monthDown = now.getMonth() + 1;
                $scope.dateDownloadNew = now.getDate() + '.' + monthDown + '.' + now.getFullYear()+'г.';
                $scope.dateDownload = $scope.activations[0].dateDownload;
            });
    }

    $scope.linkTomain= function() {
        location.replace('/3cad');
    }

    $scope.saveToPdf = function() {
        $scope.linkpdf = 'В разработке';
        // $http.get('/3cad/api/v1/activations/pdf')
        //     .then(function (response) {
        //         $scope.linkpdf = response.data.message;
        // });
    }

    $scope.init();
});