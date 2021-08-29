package me.ichun.mods.ichunutil.api.worldportals;

import net.minecraftforge.api.distmarker.OnlyIn;

public class ApiDummy implements IApi
{
    @Override
    @OnlyIn(net.minecraftforge.api.distmarker.Dist.CLIENT)
    public int getRenderLevel()
    {
        return 0;
    }

    @Override
    @OnlyIn(net.minecraftforge.api.distmarker.Dist.CLIENT)
    public float getCameraRoll(int renderLevel, float partialTick)
    {
        return 0F;
    }
}
