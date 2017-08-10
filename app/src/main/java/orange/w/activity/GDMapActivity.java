package orange.w.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;

import orange.w.R;
import orange.w.common.APP;
import orange.w.overlay.DrivingRouteOverlay;
import orange.w.utils.LogUtil;

import static com.amap.api.services.route.RouteSearch.DRIVING_SINGLE_DEFAULT;

/**
 * 显示地图
 */

public class GDMapActivity extends Activity implements LocationSource {
    private MapView mMapView;
    private AMap aMap;
    private int count;
    private RouteSearch routeSearch;
    private RouteSearch.DriveRouteQuery query;
    private LatLonPoint start;
    private LatLonPoint end;
    private RouteSearch.FromAndTo fromAndTo;
    private String BDURI = "baidumap://map/direction?destination=name:%s|latlng:%s,%s&mode=driving";//百度地图URI 起点默认,终点设置
    private String GDURI="androidamap://route?sourceApplication=amap&slat=%s&slon=%s&dlat=%s&dlon=%s&sname=%s&dname=%s&dev=1&t=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gdmap);
        mMapView = (MapView) findViewById(R.id.mv_gd);
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        getLocation();
        findViewById(R.id.bt_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                searchRoute();
            }
        });
        findViewById(R.id.bt_baidu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGaoDeMap();
//                starBaiduMap();
            }
        });
    }

    private void starBaiduMap() {
        Intent i1 = new Intent();
        // 驾车路线规划
        i1.setData(Uri.parse(String.format(BDURI,"天安门",end.getLatitude(),end.getLongitude())));
        startActivity(i1);
    }

    private void startGaoDeMap() {
        Intent intent = new Intent();
        //将功能Scheme以URI的方式传入data
        intent.setData(Uri.parse(String.format(GDURI,start.getLatitude(),start.getLongitude(),end.getLatitude(),end.getLongitude(),"齐鲁软件园","天安门")));
        //启动该页面即可
        startActivity(intent);
    }

    /**
     * 搜索路线
     */
    private void searchRoute() {
        routeSearch = new RouteSearch(this);
        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {
            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
                LogUtil.e("路线查询返回2", driveRouteResult.getPaths().size() + "");
                final DrivePath drivePath = driveRouteResult.getPaths()
                        .get(0);
                DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(APP.appContext, aMap, drivePath, driveRouteResult.getStartPos(),
                        driveRouteResult.getTargetPos(), null);
                drivingRouteOverlay.removeFromMap();
                drivingRouteOverlay.setNodeIconVisibility(false);//隐藏转弯的节点
                drivingRouteOverlay.setRouteWidth(10);
                drivingRouteOverlay.addToMap();
                drivingRouteOverlay.zoomToSpan();
            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {
            }
        });
        getXY("天安门", "北京市", 2);
        getXY("齐鲁软件园", "济南市", 1);

    }

    private void getXY(final String name, final String city, final int type) {
        final GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                LogUtil.e("地址解析返回1", i + "");
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                ++count;
                if (type == 1) {
                    start = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint();
                }
                if (type == 2) {
                    end = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint();
                }
                if (count == 2) {
                    startRouteSearch();
                }
                GeocodeQuery geocodeQuery = geocodeResult.getGeocodeQuery();
                LogUtil.e("地址解析返回数据", "城市" + geocodeQuery.getCity() + "" + geocodeQuery.getLocationName());
            }
        });
        GeocodeQuery query1 = new GeocodeQuery(name, city);
        geocodeSearch.getFromLocationNameAsyn(query1);
    }

    private void startRouteSearch() {
        fromAndTo = new RouteSearch.FromAndTo(start, end);
        query = new RouteSearch.DriveRouteQuery(fromAndTo, DRIVING_SINGLE_DEFAULT, null, null, "");
        try {
            LogUtil.e("查看计数信息", count + "");
            routeSearch.calculateDriveRouteAsyn(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 地图定位小蓝点  获得当前位置
     */
    public void getLocation() {
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(new AMapLocationListener() {
                @Override
                public void onLocationChanged(AMapLocation aMapLocation) {
                    if (mListener != null && aMapLocation != null) {
                        if (aMapLocation != null
                                && aMapLocation.getErrorCode() == 0) {
                            mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                            LogUtil.e("定位信息", aMapLocation.getCity() + aMapLocation.getDistrict() + aMapLocation.getAddress());
                        } else {
                            String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                            Log.e("AmapErr", errText);
                        }
                    }
                }
            });
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);

            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }
}
