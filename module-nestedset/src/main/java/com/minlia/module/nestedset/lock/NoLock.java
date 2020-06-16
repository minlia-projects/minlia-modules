/*
 *  The MIT License
 *
 *  Copyright (c) 2019 eXsio.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 *  rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *  The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 *  the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 *  BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 *  CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 *  ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.minlia.module.nestedset.lock;

import com.minlia.module.nestedset.NestedNodeRepository;
import com.minlia.module.nestedset.model.NestedSet;

import java.io.Serializable;

/**
 *  Default no-op Lock implementation
 *
 * @param <ID> - Nested Node Identifier Class
 * @param <N> - Nested Node Class
 */
public class NoLock<ID extends Serializable, N extends NestedSet<ID>> implements NestedNodeRepository.Lock<ID, N> {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean lockNode(N node) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unlockNode(N node) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean lockRepository() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unlockRepository() {
    }
}
