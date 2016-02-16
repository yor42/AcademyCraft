/**
 * Copyright (c) Lambda Innovation, 2013-2016
 * This file is part of the AcademyCraft mod.
 * https://github.com/LambdaInnovation/AcademyCraft
 * Licensed under GPLv3, see project root for more information.
 */
package cn.academy.misc.media;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author KSkun
 */
public class ACMedia {

    private File file;

    private String author;

    private String name;

    private File coverPic;

    private String id;

    private String remark;

    public ACMedia(String _id) {
        id = _id;
    }

    /**
     * Get the sound file of the media.
     * @return The sound file.
     */
    public File getFile() {
        return file;
    }

    /**
     * Get the author of the media.
     * @return The author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Get the display name of the media.
     * @return The display name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the cover picture of the record.
     * @return The cover pic.
     */
    public File getCoverPic() {
        return coverPic;
    }

    /**
     * Get the remark of the media.
     * @return The remark of the media.
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Get the ID of the media.
     * @return The ID of the media.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the sound file of the media.
     * @param path The path of the sound file.
     * @return this
     * @throws URISyntaxException
     */
    public ACMedia setFile(URL path) throws URISyntaxException {
        setFile(new File(path.toURI()));
        return this;
    }

    /**
     * Set the sound file of the media.
     * @param path The path of the sound file.
     * @return this
     */
    public ACMedia setFile(String path) {
        setFile(new File(path));
        return this;
    }

    /**
     * Set the sound file of the media.
     * @param _file The sound file.
     * @return this
     */
    public ACMedia setFile(File _file) {
        file = _file;
        return this;
    }

    /**
     * Set the display name of the media.
     * @param _name The display name.
     * @return this
     */
    public ACMedia setName(String _name) {
        name = _name;
        return this;
    }

    /**
     * Set the author of the media.
     * @param _author The author.
     * @return
     */
    public ACMedia setAuthor(String _author) {
        author = _author;
        return this;
    }

    /**
     * Set the cover picture of the record.
     * @param path The path of the cover pic.
     * @return this
     * @throws URISyntaxException
     */
    public ACMedia setCoverPic(URL path) throws URISyntaxException {
        setCoverPic(new File(path.toURI()));
        return this;
    }

    /**
     * Set the cover picture of the record.
     * @param path The path of the cover pic.
     * @return this
     */
    public ACMedia setCoverPic(String path) {
        setCoverPic(new File(path));
        return this;
    }

    /**
     * Set the cover picture of the record.
     * @param _file The cover pic file.
     * @return this
     */
    public ACMedia setCoverPic(File _file) {
        coverPic = _file;
        return this;
    }

    /**
     * Set the remark picture of the media.
     * @param _remark The remark.
     * @return this
     */
    public ACMedia setRemark(String _remark) {
        remark = _remark;
        return this;
    }

    /**
     * Get the availability of the media.
     * @param player The player to get.
     * @return If the media is available.
     */
    public boolean isAvailable(EntityPlayer player) {
        return true;
    }

}
