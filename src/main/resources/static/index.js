angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextpath = '/3cad';

    $scope.init = function () {
        $scope.mainPage = contextpath;
        $scope.bygroupPage = contextpath + '/bygroup/index.html';
        $scope.show();
    };

    $scope.show = function () {
        $scope.linkpdf = '';
        $http.get(contextpath + '/api/v1/activations')
            .then(function (response) {
                $scope.activations = response.data;
                $scope.count = $scope.activations.length;
                var now = new Date($scope.activations[1].dateDownloadNew);
                var monthDown = now.getMonth() + 1;
                $scope.dateDownloadNew = now.getDate() + '.' + monthDown + '.' + now.getFullYear()+'г.';
                $scope.dateDownload = $scope.activations[1].dateDownload;
            });
    }

    $scope.linkTobygroup = function() {
        // alert('Получил привет');
        location.replace($scope.bygroupPage);
        // location.replace(contextPath + '/bygroup/index.html');
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