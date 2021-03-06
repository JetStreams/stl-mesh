/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version. 
 */
var Jetstreams = (function (window, gl) {
    var SCALE = .0000008;
    var SPEED = 0.005;
    
    var document, container, stats;
    var camera, scene, renderer, controls;
    var globe, wind;
    var rotate = true;
    var direction = 1;

    function create() {

        document = window.document;
        //create and add container
        container = document.createElement('div');
        document.body.appendChild(container);
        
        scene = new gl.Scene();
        
        camera = new gl.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 1, 20);
        camera.position.z = 10;
        
        addStats();

        addLights();

        // renderer
        createRenderer();

        //controls
        createControls();

        container.appendChild(renderer.domElement);
        
        window.addEventListener('resize', onWindowResize, false);
    }

    function addLights() {
        scene.add(new gl.AmbientLight( 0xaaaaaa ));
        
        var light = new gl.PointLight(0xaa0000, 1, 100);
        light.position.set(0, 0, 0);
        scene.add(light);

        addDirectLight(0, 0, 10, 0xBEBEBE, 2);
    }

    function addDirectLight(x, y, z, color, intensity) {

        var light = new gl.DirectionalLight(color, intensity);
        light.position.set(x, y, z);
        scene.add(light);

//        light.castShadow = true;
        light.shadowCameraVisible = true;

        var d = 2;
        light.shadowCameraLeft = -d;
        light.shadowCameraRight = d;
        light.shadowCameraTop = d;
        light.shadowCameraBottom = -d;

        light.shadowCameraNear = 1;
        light.shadowCameraFar = 4;

        light.shadowBias = -0.005;
        light.shadowDarkness = 0.15;
    }

    function createRenderer() {
        renderer = new gl.WebGLRenderer({antialias: true});
        renderer.setSize(window.innerWidth, window.innerHeight);
        renderer.gammaInput = true;
        renderer.gammaOutput = true;
        renderer.shadowMapEnabled = true;
        renderer.shadowMapCullFace = gl.CullFaceBack;
    }

    function createControls() {
        controls = new gl.TrackballControls(camera);
        controls.noPan = true;
        controls.zoomSpeed = 1;
        controls.maxDistance = 12;

        controls.addEventListener('change', render);
        
        //listener for key pressed
        document.addEventListener('keypress', function(event){
            var keyCode = event.keyCode;
            if(keyCode === 115){
                rotate = !rotate;
            }else if(keyCode === 114){
                direction = 1;
            }else if(keyCode === 108){
                direction = -1;
            }
        });
    }

    function loadAll(type, callback) {
        loadGlobe(type, callback);
        loadWind();
    }

    function loadGlobe(type, callback) {

        var loadListener = function (event) {
            var geometry = event.content;
            var material = new gl.MeshLambertMaterial({ambient: 0x555555, color: 0xAAAAFF});
            globe = new gl.Mesh(geometry, material);
            globe.rotation.set(-Math.PI / 2, 0, 0);
            //change scale if you choose a mesh with a different size
            globe.scale.set(SCALE, SCALE, SCALE);

            globe.castShadow = true;
            globe.receiveShadow = true;
            scene.add(globe);

            console.log("globe loaded");
            if (typeof callback === "function") {
                callback();
            }
        };
        loadMesh('globe/' + type, loadListener);
    }

    function loadWind() {

        var loadListener = function (event) {
            var geometry = event.content;
            var material = new gl.MeshLambertMaterial({ambient: 0x000000, color: 0xAAAAAA});
            wind = new gl.Mesh(geometry, material);
            wind.rotation.set(-Math.PI / 2, 0, 0);
            //change scale if you choose a mesh with a different size
            wind.scale.set(SCALE, SCALE, SCALE);

            scene.add(wind);
            console.log("wind loaded");
        };

        loadMesh('wind', loadListener);
    }

    function loadMesh(path, loadListener) {
        var loader = new gl.STLLoader();
        loader.addEventListener('load', loadListener);
        loader.load(path);
    }

    //add statistics
    function addStats() {
        // stats
        stats = new Stats();
        stats.domElement.style.position = 'absolute';
        stats.domElement.style.bottom = '0px';
        stats.domElement.style.right = '0px';
        container.appendChild(stats.domElement);
    }

    function onWindowResize() {

        camera.aspect = window.innerWidth / window.innerHeight;
        camera.updateProjectionMatrix();
        renderer.setSize(window.innerWidth, window.innerHeight);
        controls.handleResize();
    }

    function animate() {

        requestAnimationFrame(animate);
        if (rotationEnabled()) {
            globe.rotation.z += direction * SPEED;
            wind.rotation.z += direction * SPEED;
        }
        controls.update();
        render();
    }
    
    function rotationEnabled() {
        return rotate && (typeof globe !== "undefined") && (typeof wind !== "undefined");
    }
    
    function render() {
        renderer.render(scene, camera);
        stats.update();
    }

    function updateScene(type, callback) {
        if (scene) {
            scene.remove(globe);
            var f = function () {
                callback();
                wind.rotation.z = 0.0;
            };
            loadGlobe(type, f);
        }
    }

    /** public visible */
    return {
        run: function (type, loadedCallback) {
            create();
            loadAll(type, loadedCallback);
            animate();
        },
        update: function (type, loadedCallback) {
            updateScene(type, loadedCallback);
        }
    };
})(window, THREE);
