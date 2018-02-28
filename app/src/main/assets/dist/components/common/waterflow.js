// { "framework": "Vue"} 

/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 46);
/******/ })
/************************************************************************/
/******/ ({

/***/ 46:
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(7)
)

/* script */
__vue_exports__ = __webpack_require__(8)

/* template */
var __vue_template__ = __webpack_require__(9)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "/Users/Adxyun_Karson/Desktop/工作/AdxYun/Weex/练习/weex/仿写项目/yanxuan-karson/src/components/common/waterflow.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-2d3cf4f6"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof weex === "object" && weex && weex.document) {
  try {
    weex.document.registerStyleSheets(__vue_options__._scopeId, __vue_styles__)
  } catch (e) {}
}

module.exports = __vue_exports__
module.exports.el = 'true'
new Vue(module.exports)


/***/ }),

/***/ 7:
/***/ (function(module, exports) {

module.exports = {
  "back-div": {
    "backgroundColor": "#FFFFFF"
  },
  "header": {
    "backgroundColor": "rgb(255,255,255)",
    "alignItems": "center",
    "justifyContent": "center",
    "width": "750",
    "height": "108"
  },
  "headertext": {
    "textAlign": "center",
    "fontSize": "30",
    "color": "rgb(0,0,0)"
  },
  "container": {
    "flexDirection": "row",
    "flexWrap": "wrap",
    "justifyContent": "space-between",
    "marginLeft": "30",
    "marginRight": "30",
    "backgroundColor": "#FFFFFF"
  },
  "item": {
    "backgroundColor": "#FFFFFF",
    "width": "210",
    "height": "380",
    "marginBottom": "30"
  },
  "item-img": {
    "width": "210",
    "height": "210",
    "backgroundColor": "rgb(244,244,244)",
    "borderRadius": "7"
  },
  "labcontainer": {
    "marginTop": "13",
    "flexDirection": "row",
    "flexWrap": "wrap"
  },
  "boomlab": {
    "backgroundColor": "rgb(221,162,84)",
    "borderRadius": "5",
    "marginRight": "12"
  },
  "selllab": {
    "backgroundColor": "rgb(227,124,125)",
    "borderRadius": "5",
    "marginRight": "10"
  },
  "labtext": {
    "paddingTop": "3",
    "paddingRight": "17",
    "paddingBottom": "3",
    "paddingLeft": "17",
    "color": "#FFFFFF",
    "textAlign": "center",
    "fontSize": "20"
  },
  "itemtitle-container": {
    "marginTop": "13"
  },
  "itemtitle": {
    "textAlign": "left",
    "fontSize": "25",
    "color": "#000000",
    "lines": 2,
    "textOverflow": "ellipsis"
  },
  "pricetext": {
    "marginTop": "15",
    "fontSize": "25",
    "color": "rgb(179,40,49)"
  }
}

/***/ }),

/***/ 8:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var modal = weex.requireModule('modal');
exports.default = {
    props: {
        textValue: {
            type: String,
            default: ''
        }
    },
    data: function data() {
        var dataarr = [{
            src: 'http://yanxuan.nosdn.127.net/3108aaae80416b1cf27c3d7cc5661cea.png?imageView&quality=85&thumbnail=330x330',
            desc: '男士咖啡碳+5℃升级保暖内衣',
            boomflag: '爆品',
            sellflag: '今日特价',
            price: '¥ 59'
        }, {
            src: 'http://yanxuan.nosdn.127.net/3e1c00ce7b49a78e645538a8c45cabcb.png?imageView&quality=85&thumbnail=330x330',
            desc: '经典100%羊绒格纹围巾 "',
            boomflag: '爆品',
            sellflag: '应季特惠',
            price: '¥ 139'
        }, {
            src: 'http://yanxuan.nosdn.127.net/e5474a8f4fd5748079e2ba2ead806b51.png?imageView&quality=85&thumbnail=330x330',
            desc: '黑凤梨 20寸全铝镁合金机箱',
            boomflag: '',
            sellflag: '',
            price: '¥ 699'
        }, {
            src: 'http://yanxuan.nosdn.127.net/455eee1712358dbcb2e33d54ab287808.png?imageView&quality=85&thumbnail=330x330',
            desc: '日式绒里男/女家居拖鞋',
            boomflag: '',
            sellflag: '',
            price: '¥ 39.9'
        }, {
            src: 'http://yanxuan.nosdn.127.net/795884dc6d995eca9a091a6358e3634d.png?imageView&quality=85&thumbnail=330x330',
            desc: '保暖嫩肤蚕丝羽绒子母被',
            boomflag: '爆品',
            sellflag: '',
            price: '¥ 1999'
        }, {
            src: 'http://yanxuan.nosdn.127.net/0e9ddf1a0ed5af78e8ec12cb9489df17.png?imageView&quality=85&thumbnail=330x330',
            desc: '冬眠暖绒四件套',
            boomflag: '',
            sellflag: '',
            price: '¥ 499'
        }];
        return {
            itemsone: dataarr,
            colorselected: '2色可选'
        };
    },

    methods: {
        isShow: function isShow(flagStr) {
            if (flagStr === '') {
                return false;
            }
            return true;
        }
    }
};

/***/ }),

/***/ 9:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["back-div"]
  }, [_c('div', {
    staticClass: ["header"]
  }, [_c('text', {
    staticClass: ["headertext"],
    attrs: {
      "value": _vm.textValue
    }
  })]), _c('div', {
    staticClass: ["container"]
  }, _vm._l((_vm.itemsone), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: ["item"]
    }, [_c('image', {
      staticClass: ["item-img"],
      attrs: {
        "src": item.src,
        "resize": "contain"
      }
    }), (_vm.isShow(item.boomflag) || _vm.isShow(item.sellflag)) ? _c('div', {
      staticClass: ["labcontainer"]
    }, [(_vm.isShow(item.boomflag)) ? _c('div', {
      staticClass: ["boomlab"]
    }, [_c('text', {
      staticClass: ["labtext"],
      attrs: {
        "value": item.boomflag
      }
    })]) : _vm._e(), (_vm.isShow(item.sellflag)) ? _c('div', {
      staticClass: ["selllab"]
    }, [_c('text', {
      staticClass: ["labtext"],
      attrs: {
        "value": item.sellflag
      }
    })]) : _vm._e()]) : _vm._e(), _c('div', {
      staticClass: ["itemtitle-container"]
    }, [_c('text', {
      staticClass: ["itemtitle"],
      attrs: {
        "value": item.desc
      }
    })]), _c('text', {
      staticClass: ["pricetext"],
      attrs: {
        "value": item.price
      }
    })])
  }))])
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ })

/******/ });