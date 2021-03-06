require.config({
    baseUrl: './js',
    paths: {
        jquery: "jquery/jquery-1.11.1.min",
        jqueryui: "jquery/jquery-ui.min",
        primeui: "primeui/primeui-1.1.min",
        threeCore: "threejs/three.min",
        stats: "threejs/stats.min",
        detector: "threejs/Detector.min",
        stlLoader: "threejs/STLLoader.min",
        typedGeometry: "threejs/TypedGeometry.min",
        trackBallControls: "threejs/TrackballControls.min",
        jetstreams: "webgl/jetstreams",
        webglUi: "webgl/ui"
    },
    shim: {
        primeui: {
            deps: ["jquery", "jqueryui"],
            exports: "$"
        },
        detector: {exports: 'Detector'},
        stats: {exports: 'Stats'},
        threeCore: {exports: 'THREE'},
        stlLoader: {
            deps: ['threeCore'],
            exports: 'THREE'
        },
        typedGeometry: {
            deps: ['threeCore'],
            exports: 'THREE'
        },
        trackBallControls: {
            deps: ['threeCore'],
            exports: 'THREE'
        },
        jetstreams: {
            deps: ["stats", "threeCore", "stlLoader", "typedGeometry", "trackBallControls"],
            exports: "Jetstreams"
        },
        webglUi: {
            deps: ["detector", "primeui", "jetstreams"],
            exports: "UI"
        }
    }
});

require(["primeui", "jetstreams", "webglUi"], function ($) {
    if (!Detector.webgl) {
        Detector.addGetWebGLMessage();
    } else {
        UI.create('ws');
    }
});