window.onload = function() {
    window.ui = SwaggerUIBundle({
        url: "/documentation",
        dom_id: '#swagger-ui',
        presets: [
            SwaggerUIBundle.presets.apis,
            SwaggerUIStandalonePreset
        ],
        layout: "StandaloneLayout"
    });
};