/*
 * Copyright 2017 SciJava.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.scijava.jupyter.notebook.converter;

import net.imagej.Dataset;
import net.imagej.axis.Axes;
import net.imagej.notebook.DefaultImageJNotebookService;
import net.imagej.notebook.ImageJNotebookService;
import net.imglib2.img.Img;
import org.scijava.Priority;
import org.scijava.convert.Converter;
import org.scijava.jupyter.notebook.converter.ouput.PNGImageNotebookOutput;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Converter.class, priority = Priority.LOW_PRIORITY)
public class DatasetToPNGNotebookConverter<O extends Dataset>
        extends NotebookOutputConverter<O, PNGImageNotebookOutput> {

    @Parameter
    private DefaultImageJNotebookService ijnb;
    
    @Override
    public Class getInputType() {
        return Dataset.class;
    }

    @Override
    public Class getOutputType() {
        return PNGImageNotebookOutput.class;
    }

    @Override
    public PNGImageNotebookOutput convert(Object object) {
        
        Dataset source = (Dataset) object;
        
        String base64Image = (String) ijnb.display((Img) source, //
                source.dimensionIndex(Axes.X),
                source.dimensionIndex(Axes.Y),
                source.dimensionIndex(Axes.CHANNEL),
                ImageJNotebookService.ValueScaling.AUTO);
        
        return new PNGImageNotebookOutput(PNGImageNotebookOutput.getMimeType(), base64Image);
    }

}
