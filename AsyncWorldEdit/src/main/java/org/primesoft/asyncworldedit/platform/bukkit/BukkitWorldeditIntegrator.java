
/*
 * AsyncWorldEdit a performance improvement plugin for Minecraft WorldEdit plugin.
 * Copyright (c) 2016, SBPrime <https://github.com/SBPrime/>
 * Copyright (c) AsyncWorldEdit contributors
 *
 * All rights reserved.
 *
 * Redistribution in source, use in source and binary forms, with or without
 * modification, are permitted free of charge provided that the following 
 * conditions are met:
 *
 * 1.  Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 * 2.  Redistributions of source code, with or without modification, in any form
 *     other then free of charge is not allowed,
 * 3.  Redistributions of source code, with tools and/or scripts used to build the 
 *     software is not allowed,
 * 4.  Redistributions of source code, with information on how to compile the software
 *     is not allowed,
 * 5.  Providing information of any sort (excluding information from the software page)
 *     on how to compile the software is not allowed,
 * 6.  You are allowed to build the software for your personal use,
 * 7.  You are allowed to build the software using a non public build server,
 * 8.  Redistributions in binary form in not allowed.
 * 9.  The original author is allowed to redistrubute the software in bnary form.
 * 10. Any derived work based on or containing parts of this software must reproduce
 *     the above copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided with the
 *     derived work.
 * 11. The original author of the software is allowed to change the license
 *     terms or the entire license of the software as he sees fit.
 * 12. The original author of the software is allowed to sublicense the software
 *     or its parts using any license terms he sees fit.
 * 13. By contributing to this project you agree that your contribution falls under this
 *     license.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.primesoft.asyncworldedit.platform.bukkit;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import static org.primesoft.asyncworldedit.LoggerProvider.log;
import org.primesoft.asyncworldedit.api.IWorld;
import org.primesoft.asyncworldedit.api.inner.IAsyncWorldEditCore;
import org.primesoft.asyncworldedit.api.playerManager.IPlayerEntry;
import org.primesoft.asyncworldedit.worldedit.WorldEditIntegrator;

/**
 *
 * @author SBPrime
 */
public class BukkitWorldeditIntegrator extends WorldEditIntegrator {
    private static final BlockData AIR = Material.AIR.createBlockData();
    
    private WorldEditPlugin m_worldEditPlugin;

    /**
     * Create new instance of world edit integration checker and start it
     *
     * @param aweCore
     * @param worldEdit
     */
    public BukkitWorldeditIntegrator(IAsyncWorldEditCore aweCore, WorldEditPlugin worldEdit) {
        super(aweCore);
        
        initialize(worldEdit);
        
        initializationDone();
    }

    /**
     * Initialize the WorldEdit integrator
     *
     * @param wePlugin 
     */
    private void initialize(WorldEditPlugin wePlugin) {                        
        if (wePlugin == null) {
            log("Error initializeing Worldedit integrator");
            return;
        }

        initialize(wePlugin.getWorldEdit());
        
        m_worldEditPlugin = wePlugin;
    }

    /**
     * Get the WorldEdit player wrapper
     *
     * @param player
     * @return
     */
    @Override
    public com.sk89q.worldedit.entity.Player wrapPlayer(IPlayerEntry player) {
        if (!(player instanceof BukkitPlayerEntry)) {
            return null;
        }

        Player bPlayer = ((BukkitPlayerEntry) player).getPlayer();
        if (bPlayer == null) {
            return null;
        }

        return m_worldEditPlugin.wrapPlayer(bPlayer);
    }

    @Override
    public World getWorld(IWorld world) {
        if (!(world instanceof BukkitWorld)) {
            return null;
        }
        
        return new com.sk89q.worldedit.bukkit.BukkitWorld(((BukkitWorld)world).getWorld());
    }

    @Override
    public IWorld getWorld(World world) {
        if (world == null) {
            return null;            
        }
        
        
        if (!(world instanceof com.sk89q.worldedit.bukkit.BukkitWorld)) {
            return super.getWorld(world);
        }
        
        com.sk89q.worldedit.bukkit.BukkitWorld bWorld = (com.sk89q.worldedit.bukkit.BukkitWorld)world;
        
        return new BukkitWorld(bWorld.getWorld());
    }
}
