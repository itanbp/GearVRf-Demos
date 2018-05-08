package org.gearvrf.arcore.simplesample;

import org.gearvrf.GVRCameraRig;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRDirectLight;
import org.gearvrf.GVRImportSettings;
import org.gearvrf.GVRMain;
import org.gearvrf.GVRPointLight;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRTransform;
import org.gearvrf.utility.Log;

import java.io.IOException;
import java.util.EnumSet;

/**
 * Created by itanbp on 06/05/2018.
 */

public class VRSampleMain extends GVRMain {


    @Override
    public void onInit(GVRContext gvrContext) {
        GVRScene scene = gvrContext.getMainScene();
        GVRSceneObject model;
        GVRDirectLight headLight = new GVRDirectLight(gvrContext);
        GVRSceneObject lightObj = new GVRSceneObject(gvrContext);
        GVRPointLight toplight = new GVRPointLight(gvrContext);
        EnumSet<GVRImportSettings> settings = GVRImportSettings.getRecommendedSettingsWith(EnumSet.of(GVRImportSettings.TRIANGULATE, GVRImportSettings.CALCULATE_NORMALS));

        scene.getMainCameraRig().getHeadTransformObject().attachComponent(headLight);
        scene.getRoot().attachComponent(toplight);
        scene.setBackgroundColor(1, 1, 0.2f, 1);
        headLight.setAmbientIntensity(0.3f, 0.3f, 0.3f, 1.0f);
        headLight.setDiffuseIntensity(0.7f, 0.7f, 0.7f, 1.0f);
        toplight.setAmbientIntensity(0.3f, 0.3f, 0.3f, 1.0f);
        toplight.setDiffuseIntensity(0.7f, 0.7f, 0.7f, 1.0f);
        lightObj.attachComponent(toplight);
        lightObj.getTransform().setPosition(0, 2, 0);
        scene.addSceneObject(lightObj);

        GVRCameraRig cameraRig = gvrContext.getMainScene().getMainCameraRig();
        cameraRig.getLeftCamera().setBackgroundColor(1.0f, 0.0f, 0.0f, 1.0f);
        cameraRig.getRightCamera().setBackgroundColor(0.0f, 1.0f, 0.0f, 1.0f);

        try
        {
            model = gvrContext.getAssetLoader().loadModel("objects/CUPIC_SUBMARINE.obj", settings, true, scene);
            centerModel(model, scene.getMainCameraRig().getTransform());
            model.getTransform().rotateByAxis(45.0f, 0, 1, 0);
        }
        catch (IOException e)
        {
            Log.e("ASSET", e.getMessage());
        }
    }


    private void centerModel(GVRSceneObject model, GVRTransform camTrans) {
        GVRSceneObject.BoundingVolume bv = model.getBoundingVolume();
        float x = camTrans.getPositionX();
        float y = camTrans.getPositionY();
        float z = camTrans.getPositionZ();
        float sf = 1 / bv.radius;
        model.getTransform().setScale(sf, sf, sf);
        bv = model.getBoundingVolume();
        model.getTransform().setPosition(x - bv.center.x, y - bv.center.y, z - bv.center.z - 1.5f);
    }

}
