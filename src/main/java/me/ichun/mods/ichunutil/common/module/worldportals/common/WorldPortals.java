package me.ichun.mods.ichunutil.common.module.worldportals.common;

import me.ichun.mods.ichunutil.api.worldportals.WorldPortalsApi;
import me.ichun.mods.ichunutil.client.render.RenderHelper;
import me.ichun.mods.ichunutil.common.network.PacketChannel;
import me.ichun.mods.ichunutil.common.iChunUtil;
import me.ichun.mods.ichunutil.common.module.worldportals.client.core.EventHandlerWorldPortalClient;
import me.ichun.mods.ichunutil.common.module.worldportals.common.core.ApiImpl;
import me.ichun.mods.ichunutil.common.module.worldportals.common.core.EventHandlerWorldPortal;
import me.ichun.mods.ichunutil.common.module.worldportals.common.packet.PacketEntityLocation;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class WorldPortals
{
    private static boolean init = false;

    public static EventHandlerWorldPortalClient eventHandlerClient;
    public static EventHandlerWorldPortal eventHandler;
    public static PacketChannel channel;

    public static void init()
    {
        if(init)
        {
            return;
        }
        init = true;

        iChunUtil.config.reveal("maxRecursion", "stencilValue", "renderDistanceChunks", "maxRendersPerTick");

        eventHandler = new EventHandlerWorldPortal();
        MinecraftForge.EVENT_BUS.register(eventHandler);

        WorldPortalsApi.setApiImpl(new ApiImpl());

        channel = new PacketChannel("iChun_WorldPortals", PacketEntityLocation.class);
        
        initClient();
    }

    @OnlyIn(net.minecraftforge.api.distmarker.Dist.CLIENT)
    private static void initClient()
    {
        eventHandlerClient = new EventHandlerWorldPortalClient();
        MinecraftForge.EVENT_BUS.register(eventHandlerClient);
        Minecraft mc = Minecraft.getInstance();
        if(!mc.getFramebuffer().isStencilEnabled())
        {
        	mc.getFramebuffer().enableStencil();
            //iChunUtil.LOGGER.error("[WorldPortals] Stencils aren't enabled. We won't be able to render a world portal!");
        }
        //because why not
        if(!mc.getFramebuffer().isStencilEnabled())
        {
            iChunUtil.LOGGER.error("[WorldPortals] Stencils aren't enabled. We won't be able to render a world portal!");
        }
    }

    public static void onServerStopping()
    {
        if(init)
        {
            eventHandler.monitoredEntities.get(Side.SERVER).clear();
        }
    }
}
